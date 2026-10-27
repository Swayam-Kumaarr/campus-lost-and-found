public final class ValuableFoundItem extends FoundItem {

    private String secretDetail;

    public ValuableFoundItem(String name, String category, String location, String reporter, String contact,
                             String handedTo, String secretDetail) {
        super(name, category, location, reporter, contact, handedTo);
        this.secretDetail = secretDetail;
    }

    public boolean checkSecret(String answer) { return secretDetail.equalsIgnoreCase(answer); }

    @Override
    public String getType() { return "FOUND-VALUABLE"; }

    @Override
    public String getExtraInfo() { return super.getExtraInfo() + " | Owner must verify a hidden detail"; }

    @Override
    public String toFileLine() { return "VALUABLE|" + commonFields() + "|" + handedTo + "|" + secretDetail; }
}
