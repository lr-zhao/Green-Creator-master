package hluwa.dex.format;

import hluwa.dex.base.Pool;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DexFile {
    private Pool<Class_Def_Item> class_def_list;
    private Pool<Field_Id_Item> field_id_list;
    private File file;
    private DexHeader header;
    public byte[] link_data;
    private MapList map_list;
    private Pool<Method_Id_Item> method_id_list;
    private Pool<Proto_Id_Item> proto_id_list;
    private Pool<String_Data_Item> string_data_list;
    private Pool<String_Id_Item> string_id_list;
    private Pool<Type_Id_Item> type_id_list;
    private Pool<Type_List_Item> type_list_list;

    public DexFile(File file2) {
        this.file = file2;
    }

    public DexFile() {
    }

    public Map_Item getMap(int type) {
        MapList list = getMap_list();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).type == type) {
                return list.get(i);
            }
        }
        return null;
    }

    public static boolean isDexFile(byte[] bytes) {
        return bytes != null && bytes.length >= 4 && bytes[0] == 100 && bytes[1] == 101 && bytes[2] == 120 && bytes[3] == 10;
    }

    public static boolean isDexFile(File file2) {
        if (file2 == null) {
            return false;
        }
        try {
            InputStream in = new FileInputStream(file2);
            if (in.available() < 4) {
                in.close();
                return false;
            } else if (in.read() == 100 && in.read() == 101 && in.read() == 120 && in.read() == 10) {
                in.close();
                return true;
            } else {
                in.close();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public short findMethod(String mtdStr) {
        for (short i = 0; i < getMethod_id_list().size(); i = (short) (i + 1)) {
            if (mtdStr.equals(getName((Method_Id_Item) getMethod_id_list().get(i)))) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<String> getAllString() {
        ArrayList<String> strList = new ArrayList<String>();
        Iterator it = this.string_data_list.iterator();
        while (it.hasNext()) {
            strList.add(new String(((String_Data_Item) it.next()).body));
        }
        return strList;
    }

    public ArrayList<encoded_method> getAllEncodedMethod() {
        ArrayList<encoded_method> all = new ArrayList<>();
        Iterator it = this.class_def_list.iterator();
        while (it.hasNext()) {
            Class_Def_Item cls = (Class_Def_Item) it.next();
            if (cls.class_data != null) {
                String name = getName(cls);
                all.addAll(cls.class_data.direct_methods);
                all.addAll(cls.class_data.virtual_methods);
            }
        }
        return all;
    }

    public ArrayList<insns_item> getAllInsnsItem() {
        ArrayList<insns_item> all = new ArrayList<>();
        Iterator<encoded_method> it = getAllEncodedMethod().iterator();
        while (it.hasNext()) {
            encoded_method method = it.next();
            if (method.code != null) {
                all.addAll(method.code.insns_items);
            }
        }
        return all;
    }

    public String getNameByMethodId(int id) {
        return getName((Method_Id_Item) this.method_id_list.get(id));
    }

    public String getNameByClassId(int id) {
        return getName((Class_Def_Item) this.class_def_list.get(id));
    }

    public String getNameByTypeId(int id) {
        return getName((Type_Id_Item) this.type_id_list.get(id));
    }

    public String getNameByFieldId(int id) {
        return getName((Field_Id_Item) this.field_id_list.get(id));
    }

    public String getNameByProtoId(int id) {
        return getName((Proto_Id_Item) this.proto_id_list.get(id));
    }

    public String getName(encoded_method method) {
        return getNameByMethodId(method.real_id);
    }

    public String getName(Type_Id_Item type) {
        return getString(type.id);
    }

    public String getName(Proto_Id_Item proto) {
        return getString(proto.shorty_id);
    }

    public String getName(Field_Id_Item field) {
        return getString(field.name_id);
    }

    public String getName(Class_Def_Item cls) {
        return getNameByTypeId(cls.class_id);
    }

    public String getName(Method_Id_Item method) {
        String className = getNameByTypeId(method.class_id).replaceAll("/", "\\.");
        if (className.startsWith("L")) {
            className = className.substring(1, className.length() - 1);
        }
        return className + "." + getString(method.name_id).replaceAll("\u0000", "") + "(" + getNameByProtoId(method.proto_id).replaceAll("\u0000", "") + ")";
    }

    public String getString(int id) {
        String res = new String(((String_Data_Item) this.string_data_list.get(id)).body);
        if (!res.endsWith("\u0000") || res.length() < 2) {
            return res;
        }
        return new String(res.getBytes(), 0, res.length() - 1);
    }

    public File getFile() {
        return this.file;
    }

    public DexHeader getHeader() {
        return this.header;
    }

    public void setHeader(DexHeader header2) {
        this.header = header2;
    }

    public Pool<String_Id_Item> getString_id_list() {
        return this.string_id_list;
    }

    public void setString_id_list(Pool<String_Id_Item> string_id_list2) {
        this.string_id_list = string_id_list2;
    }

    public Pool<Type_Id_Item> getType_id_list() {
        return this.type_id_list;
    }

    public void setType_id_list(Pool<Type_Id_Item> type_id_list2) {
        this.type_id_list = type_id_list2;
    }

    public Pool<Proto_Id_Item> getProto_id_list() {
        return this.proto_id_list;
    }

    public void setProto_id_list(Pool<Proto_Id_Item> proto_id_list2) {
        this.proto_id_list = proto_id_list2;
    }

    public Pool<Method_Id_Item> getMethod_id_list() {
        return this.method_id_list;
    }

    public void setMethod_id_list(Pool<Method_Id_Item> method_id_list2) {
        this.method_id_list = method_id_list2;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public MapList getMap_list() {
        return this.map_list;
    }

    public void setMap_list(MapList map_list2) {
        this.map_list = map_list2;
    }

    public Pool<Field_Id_Item> getField_id_list() {
        return this.field_id_list;
    }

    public void setField_id_list(Pool<Field_Id_Item> field_id_list2) {
        this.field_id_list = field_id_list2;
    }

    public Pool<Class_Def_Item> getClass_def_list() {
        return this.class_def_list;
    }

    public void setClass_def_list(Pool<Class_Def_Item> class_def_list2) {
        this.class_def_list = class_def_list2;
    }

    public Pool<String_Data_Item> getString_data_list() {
        return this.string_data_list;
    }

    public void setString_data_list(Pool<String_Data_Item> string_data_list2) {
        this.string_data_list = string_data_list2;
    }

    public Pool<Type_List_Item> getType_list_list() {
        return this.type_list_list;
    }

    public void setType_list_list(Pool<Type_List_Item> type_list_list2) {
        this.type_list_list = type_list_list2;
    }
}