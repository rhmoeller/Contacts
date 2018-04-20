package tests.util;

import com.hjortsholm.contacts.util.MD5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName(value = "MD5")
public class MD5Test {

    private MD5 md5;

    @BeforeEach
    void setUp() {
        this.md5 = new MD5();
    }

    @Test
    @DisplayName(value = "Digest from the same string are the same")
    public void digestStringTwice() {
        String stringToDigest = "foobar";
        String[] generatedDigests = new String[]{
                this.md5.getDigest(stringToDigest),
                this.md5.getDigest(stringToDigest)
        };

        assertEquals(generatedDigests[0], generatedDigests[1]);
    }

    @Test
    @DisplayName(value = "Digests from different strings are different")
    public void digestDifferentStrings() {
        String[] stringsToDigest = new String[]{"foo", "bar"};
        String[] generatedDigests = new String[]{
                this.md5.getDigest(stringsToDigest[0]),
                this.md5.getDigest(stringsToDigest[1])
        };

        assertEquals(false, generatedDigests[0].equals(generatedDigests[1]));
    }
}