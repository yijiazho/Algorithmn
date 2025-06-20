package game;

public interface Exportable {
    String exportToJson();

    void importFromJson(String data);
}
