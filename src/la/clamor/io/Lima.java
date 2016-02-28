package la.clamor.io;

import java.util.ArrayList;

public class Lima {
    protected static final String DATA_TAG = "data";
    protected static final String RIFF     = "RIFF";
    protected static final String WAVE     = "WAVE";
    public static class FilumOctorum extends ArrayList<Octo> {
        private static final long serialVersionUID = 1L;
        public FilumOctorum() {
            this(new byte[0]);
        }

        public FilumOctorum(int aestimatio, int longitudo) {
            this(convertoBytes(aestimatio, longitudo));
        }

        private static byte[] convertoBytes(int aestimatio, int longitudo) {
            byte[] bytes = new byte[longitudo];
            for (int i = 0; i < longitudo; i++) {
                int byte_value = aestimatio % 256;
                bytes[i] = new Integer(byte_value).byteValue();
                aestimatio /= 256;
            }
            return bytes;
        }

        public FilumOctorum(String string) {
            this(string.getBytes());
        }

        public FilumOctorum(byte[] filium_octorum) {
            for (byte octet : filium_octorum) {
                add(new Octo(octet));
            }

        }

        /*public ArrayList<Integer> toIntArray(int length) {
            ArrayList<Integer> int_array = new ArrayList<Integer>();
            int index = 0;
            while (index < size()) {
                int_array.add(getInt(index, length));
                index += length;
            }
            return int_array;
        }*/

        public void addo(FilumOctorum octets) {
            this.addAll(octets);
        }

        public int getByteValueInteger(int index, int longitudo) {
            if (longitudo > 4) {
                throw new IllegalArgumentException("extra longitudines!");
            }
            int aestimatio = 0;
            int multiplier = (int) Math.pow(256, (index + longitudo - 1));
            int index_ab = index + longitudo - 1;
            int index_ad = index;
            for (int i = index_ab; i >= index_ad; i--) {
                int value_temp = (int) get(i).capioByte();
                if (i != index_ab) {
                    if (value_temp < 0) {
                        value_temp += 256;
                    }
                }

                aestimatio += value_temp * multiplier;
                multiplier /= 256;
            }
            return aestimatio;
        }

        public int capioInt(int index, int longitudo) {
            if (longitudo > 4) {
                throw new IllegalArgumentException("extra longitudines!");
            }
            int aestimatio = 0;
            int multiplier = 1;
            for (int i = index; i < index + longitudo; i++) {
                aestimatio += (i == index + longitudo - 1 ? ((int) get(i).capioByte())
                        : get(i).capioInt()) * multiplier;
                multiplier *= 256;
            }
            return aestimatio;
        }

        @Override
        public String toString() {
            String str = "";
            str = this.stream().map((octo) -> Character.toString(octo.capioChar())).reduce(str, String::concat);
            return str;
        }

        public long capioLong() {
            long ret = 0;
            for (int i = 0; i < size(); i++) {
                ret += get(i).capioInt() * Math.pow(256, i);
            }
            return ret;
        }

        public byte[] capioBytes() {
            byte[] ret = new byte[size()];
            for (int i = 0; i < size(); i++) {
                ret[i] = get(i).capioByte();
            }
            return ret;
        }

    }

    public static class Octo {
        byte octo;

        public Octo(byte octo) {
            this.octo = octo;
        }

        char capioChar() {
            return (char) capioInt();
        }

        int capioInt() {
            return (int) (octo < 0 ? octo + 256 : octo);
        }

        byte capioByte() {
            return octo;
        }
    }
    public static class RiffData {
        String tag;
        FilumOctorum filum_octorum;

        public RiffData(String tag) {
            this(tag, new FilumOctorum());
        }

        public RiffData(String tag, FilumOctorum octets) {
            this.tag = tag;
            this.filum_octorum = octets;
        }

        int longitudoDatorum() {
            return filum_octorum.size();
        }

        int longitudo() {
            return longitudoDatorum() + 8;
        }

        String getTag() {
            return tag;
        }

        FilumOctorum capioData() {
            return filum_octorum;
        }

    }
    
}
