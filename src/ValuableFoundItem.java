public final class ValuableFoundItem extends FoundItem {
 
    private String secretDetail;
 
    public ValuableFoundItem(String name, String category, String location, Student reporter,
                             String handedTo, String secretDetail) {
        super(name, category, location, reporter, handedTo);
        this.secretDetail = secretDetail;
    }
 
    public ValuableFoundItem(int id, String name, String category, String location, Student reporter,
                             String handedTo, String secretDetail) {
        super(id, name, category, location, reporter, handedTo);
        this.secretDetail = secretDetail;
    }
 
    public String getSecretDetail() { return secretDetail; }
 
    @Override
    public String getType() {
        return "FOUND-VALUABLE";
    }
 
    @Override
    public String getExtraInfo() {
        return super.getExtraInfo() + " | Owner must verify a hidden detail";
    }
}
