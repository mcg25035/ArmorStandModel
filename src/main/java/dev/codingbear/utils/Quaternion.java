package dev.codingbear.utils;

class Quaternion {
    private double w, x, y, z;

    // 四元數構造器
    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // 從旋轉軸和角度創建四元數
    public static Quaternion fromAxisAngle(double[] axis, double angle) {
        double sinHalfAngle = Math.sin(angle / 2);
        double cosHalfAngle = Math.cos(angle / 2);
        return new Quaternion(
                cosHalfAngle,
                sinHalfAngle * axis[0],
                sinHalfAngle * axis[1],
                sinHalfAngle * axis[2]
        );
    }

    // 四元數乘法
    public Quaternion multiply(Quaternion q) {
        return new Quaternion(
                this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z,
                this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y,
                this.w * q.y - this.x * q.z + this.y * q.w + this.z * q.x,
                this.w * q.z + this.x * q.y - this.y * q.x + this.z * q.w
        );
    }

    // 轉換為歐拉角
    public double[] toEulerAngles() {
        double[] angles = new double[3];
        // 計算歐拉角
        angles[0] = Math.atan2(2 * (w * x + y * z), 1 - 2 * (x * x + y * y));
        angles[1] = Math.asin(2 * (w * y - z * x));
        angles[2] = Math.atan2(2 * (w * z + x * y), 1 - 2 * (y * y + z * z));
        return angles;
    }
}
