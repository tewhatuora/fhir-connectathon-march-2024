package nz.govt.tewhatuora.Entity;

import java.util.Date;

public class DeathNotice {
  private String nhi;
  private Date deathDate;

  public DeathNotice(String nhi, Date deathDate) {
    this.nhi = nhi;
    this.deathDate = deathDate;
  }

  public String getNhi() {
    return nhi;
  }

  public void setNhi(String nhi) {
    this.nhi = nhi;
  }

  public Date getDeathDate() {
    return deathDate;
  }

  public void setDeathDate(Date deathDate) {
    this.deathDate = deathDate;
  }
}
