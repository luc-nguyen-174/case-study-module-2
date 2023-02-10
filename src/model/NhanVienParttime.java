package model;

import java.io.Serializable;

public class NhanVienParttime extends NhanVien implements TinhLuong, Serializable {
    private double gioLam;

    public NhanVienParttime() {
    }

    public NhanVienParttime(String maNv, String ten, String sdt, String diaChi, String email, double gioLam) {
        super(maNv, ten, sdt, diaChi, email);
        this.gioLam = gioLam;
    }

    public double getGioLam() {
        return gioLam;
    }

    public void setGioLam(double gioLam) {
        this.gioLam = gioLam;
    }

    @Override
    public String toString() {
        return "NhanVienParttime{" +
                "gioLam=" + gioLam +
                "} " + super.toString();
    }

    @Override
    public int tinhLuong() {
        return (int) (gioLam * 100000);
    }
}
