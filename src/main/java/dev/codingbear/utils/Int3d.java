package dev.codingbear.utils;

import com.google.gson.JsonArray;

import java.util.Objects;

public class Int3d {
    public int x;
    public int y;
    public int z;

    public Int3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public JsonArray toJson() {
        JsonArray json = new JsonArray();
        json.add(x);
        json.add(y);
        json.add(z);
        return json;
    }

    public static Int3d fromJson(JsonArray json) {
        return new Int3d(json.get(0).getAsInt(), json.get(1).getAsInt(), json.get(2).getAsInt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Int3d int3d = (Int3d) o;
        return x == int3d.x && y == int3d.y && z == int3d.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
