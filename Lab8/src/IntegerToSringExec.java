class IntegerToStringExec implements IExecutor<Integer, String> {
    StringBuffer line = new StringBuffer();

    @Override
    public void execute(Integer elem) {
        line.append(elem + "; ");
    }

    @Override
    public String getResult() {
        if (line.length() >= 2) {
            line.delete(line.length() - 2, line.length());
        }
        return line.toString();
    }
}

