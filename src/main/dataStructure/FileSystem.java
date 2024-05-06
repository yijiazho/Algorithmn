package dataStructure;

import java.util.*;
import java.util.stream.Collectors;


abstract class TreeNode {
    String name;
    Map<String, TreeNode> children;

    public TreeNode (String _name) {
        name = _name;
    }

    abstract public List<String> getChildren ();
}

class File extends TreeNode {
    String content;

    public File(String _name) {
        super(_name);
        content = "";
    }

    @Override
    public List<String> getChildren() {
        return new ArrayList<>();
    }

    public void append(String _content) {
        if (content == "") {
            content = _content;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        sb.append(_content);
        content = sb.toString();

    }
}

class Directory extends TreeNode {
    
    public Directory(String _name) {
        super(_name);
        children = new HashMap<>();
    }

    @Override
    public List<String> getChildren() {
        List<String> list = children.keySet().stream().collect(Collectors.toList());
        Collections.sort(list);
        return list;
    }
}

public class FileSystem {

    // use a tree structure to represent the path
    TreeNode root;
    TreeNode cur;

    public FileSystem() {
        root = new Directory("root");
        cur = root;
    }

    public List<String> ls(String path) {
        gotoPath(path, false);
        List<String> list = cur.getChildren();
        clear();
        return list;
    }

    public void mkdir(String path) {
        gotoPath(path, true);
    }

    public void addContentToFile(String filePath, String content) {
        File file = goToFilePath(filePath, true);
        file.append(content);
    }

    public String readContentFromFile(String filePath) {
        File file = goToFilePath(filePath, false);
        String content = file.content;
        clear();
        return content;
    }

    // path = "/a/b/c"
    // force means if we should create if the path does not exits
    private void gotoPath(String path, boolean force) {
        String[] array = path.split("/");
        for (int i = 0; i < array.length; i++) {
            String p = array[i];
            if (p.isEmpty()) {
                continue;
            }

            if (cur.children.containsKey(p)) {
                cur = cur.children.get(p);
            } else {
                if (force) {
                    cur.children.put(p, new Directory(p));
                    cur = cur.children.get(p);
                } else {
                    throw new IllegalArgumentException("Invalid path: " + path);
                }

            }
        }
    }

    private File goToFilePath(String path, boolean force) {
        int l = path.length();
        int i;
        for (i = l - 1; i >= 0; i--) {
            if (path.charAt(i) == '/') {
                break;
            }
        }

        String fileName = path.substring(i + 1);
        gotoPath(path.substring(0, i), true);
        if (cur.children.containsKey(fileName)) {
            return (File) cur.children.get(fileName);
        } else {
            if (force) {
                File file = new File(fileName);
                cur.children.put(fileName, file);
                return file;
            } else {
                throw new IllegalArgumentException("Cannot find file: " + fileName);
            }
        }
    }

    private void clear() {
        this.cur = root;
    }

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        fileSystem.ls("/");
        fileSystem.mkdir("a/b/c");
        fileSystem.addContentToFile("a/b/c/d", "hello");
        fileSystem.ls("/");
        fileSystem.readContentFromFile("a/b/c/d");
    }


    
}
