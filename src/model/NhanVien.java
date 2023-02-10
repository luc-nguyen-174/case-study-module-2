package model;

import java.io.Serializable;

public abstract class NhanVien implements Serializable {
    private String maNv;
    private String ten;
    private String sdt;
    private String diaChi;
    private String email;

    public NhanVien() {
    }

    public NhanVien(String maNv, String ten, String sdt, String diaChi, String email) {
        this.maNv = maNv;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNv='" + maNv + '\'' +
                ", ten='" + ten + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
