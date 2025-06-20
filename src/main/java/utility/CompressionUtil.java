package utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtil {

    private static byte[] compressData(byte[] inputData) {
        Objects.requireNonNull(inputData, "Input data cannot be null");
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(inputData);
        deflater.finish();
        byte[] buffer = new byte[1024];

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(inputData.length)) {
            while (!deflater.finished()) {
                int compressedDataLength = deflater.deflate(buffer);
                if (compressedDataLength == 0 && deflater.needsInput()) {
                    break;
                }
                outputStream.write(buffer, 0, compressedDataLength);
            }
            return outputStream.toByteArray();
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to compress data", ioe);
        }
    }

    private static byte[] deCompressData(byte[] compressedData) {
        Objects.requireNonNull(compressedData, "Compressed data cannot be null");
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);
        byte[] buffer = new byte[1024];

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(compressedData.length)) {
            while (!inflater.finished()) {
                int uncompressedLength = inflater.inflate(buffer);
                if (uncompressedLength == 0 && inflater.needsInput()) {
                    break;
                }
                outputStream.write(buffer, 0, uncompressedLength);
            }
            return outputStream.toByteArray();
        } catch (DataFormatException | IOException e) {
            throw new RuntimeException("Failed to decompress data", e);
        }
    }

    public static String decompress(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("input cannot be null or empty");
        }
        try {
            Byte[] byteArray = Arrays.stream(input.replace("[", "").replace("]", "").split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(i -> (byte) i)
                    .toArray(Byte[]::new);
            byte[] primitiveByteArray = new byte[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                primitiveByteArray[i] = byteArray[i];
            }
            return new String(CompressionUtil.deCompressData(primitiveByteArray));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid byte array string provided for input", e);
        }
    }

    public static String compress(String input) {
        Objects.requireNonNull(input, "input cannot be null");
        if (input.isEmpty()) {
            throw new IllegalArgumentException("input cannot be empty");
        }
        byte[] compressedData = compressData(input.getBytes());
        return Arrays.toString(compressedData);
    }
}
