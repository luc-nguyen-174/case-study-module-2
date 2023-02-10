package model;

import java.io.Serializable;

public class NhanVienChinhThuc extends NhanVien implements TinhLuong, Serializable {
    private int luongCung;
    private int luongThuong;
    private int phat;


    public NhanVienChinhThuc(String maNv, String ten, String sdt, String diaChi, String email, int luongCung, int luongThuong, int phat) {
        super(maNv, ten, sdt, diaChi, email);
        this.luongCung = luongCung;
        this.luongThuong = luongThuong;
        this.phat = phat;
    }

    public int getLuongCung() {
        return luongCung;
    }

    public void setLuongCung(int luongCung) {
        this.luongCung = luongCung;
    }

    public int getLuongThuong() {
        return luongThuong;
    }

    public void setLuongThuong(int luongThuong) {
        this.luongThuong = luongThuong;
    }

    public int getPhat() {
        return phat;
    }

    public void setPhat(int phat) {
        this.phat = phat;
    }

    @Override
    public String toString() {
        return "NhanVienChinhThuc{" +
                "luongCung=" + luongCung +
                ", luongThuong=" + luongThuong +
                ", phat=" + phat +
                "} " + super.toString();
    }

    @Override
    public int tinhLuong() {
        return luongCung + luongThuong - phat;
    }
}
