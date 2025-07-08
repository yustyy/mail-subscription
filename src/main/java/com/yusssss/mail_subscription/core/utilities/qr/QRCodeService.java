package com.yusssss.mail_subscription.core.utilities.qr;

import java.io.IOException;

public interface QRCodeService {
    byte[] generateQRCode(String data, int width, int height) throws IOException;

    byte[] generateQRCodeWithLogo(String data, int width, int height, String logoPath, int logoSize) throws IOException;

}
