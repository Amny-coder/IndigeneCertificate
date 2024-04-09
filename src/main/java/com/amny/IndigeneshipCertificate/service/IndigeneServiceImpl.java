package com.amny.IndigeneshipCertificate.service;


import com.amny.IndigeneshipCertificate.entity.Indigene;
import com.amny.IndigeneshipCertificate.entity.UserStatus;
import com.amny.IndigeneshipCertificate.error.FailedToDeleteIndigeneshipApplication;
import com.amny.IndigeneshipCertificate.error.FailedToUpdateIndigeneshipApplication;
import com.amny.IndigeneshipCertificate.error.NoApplicantFound;
import com.amny.IndigeneshipCertificate.error.NotBlankedException;
import com.amny.IndigeneshipCertificate.repository.IndigeneRepository;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyleConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.itextpdf.io.font.constants.StandardFonts.*;

@Service
public class IndigeneServiceImpl implements IndigeneService {

    private static final String SIGNATURE_FONT = "/Users/user/IdeaProjects/IndigeneshipCertificate/src/main/resources/static/fonts/DarlingtonDemo-z8xjG.ttf";

    @Autowired
    private IndigeneRepository indigeneRepository;

    @Override
    public Indigene saveIndigeneInfo(Indigene indigene) throws NotBlankedException {

        if (Objects.isNull(indigene.getSurname())) {
            throw new NotBlankedException("Submission Failed Please Make Sure That All Input Boxes Are " +
                    "Filled With Correct Info");
        }
        indigene.setApplicationStatus(UserStatus.PENDING);

        return indigeneRepository.save(indigene);
    }

    @Override
    public Indigene fetchById(Long userId) throws NoApplicantFound {
        Optional<Indigene> fetchByApplicantId = indigeneRepository.findById(userId);
        if (fetchByApplicantId.isEmpty()) {
            throw new NoApplicantFound("Application With ID: " + userId + " Not Found");
        }
        return fetchByApplicantId.get();
    }

    @Override
    public ByteArrayOutputStream certificateGenerator(Long userId) throws NoApplicantFound, IOException {

        Optional<Indigene> indigene = indigeneRepository.findById(userId);

        if (indigene.isEmpty()) {
            throw new NoApplicantFound("Indigene Application Not_Found Therefore Certificate Can" +
                    " Not Be Generated");
        }else {

            Indigene getFromDb = indigene.get();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            pdfDocument.setDefaultPageSize(PageSize.A4.rotate());

            Document document = new Document(pdfDocument);

            String path = "/Users/user/IdeaProjects/IndigeneshipCertificate/src/main/resources/static/image/Seal_of_Zamfara_State_Government.jpg";
            ImageData imageData = ImageDataFactory.create(path);
            Image headerLogo = new Image(imageData);

            PdfFont headerFont = PdfFontFactory.createFont(TIMES_ITALIC);

            Text headerFirstLine = new Text("Indigene Certificate\n");
            headerFirstLine
                    .setFont(headerFont)
                    .setBold()
                    .setStrokeWidth(5f)
                    .setStrokeColor(ColorConstants.BLUE)
                    .setFontSize(20f);

            Text headerSecondLine = new Text("\t\tGUSAU LOCAL GOVERNMENT\n");
            headerSecondLine.setFont(headerFont).setBold();

            Text headerThirdLine = new Text("\t\t\tZAMFARA STATE\n");
            headerThirdLine.setFont(headerFont).setFontColor(ColorConstants.RED).setBold();

            Text underLine = new Text( "-----------------------------------------------------------------------");
            underLine.setFont(headerFont).setItalic().setOpacity(50f);

            Paragraph headerText = new Paragraph();
            headerText.setTextAlignment(TextAlignment.CENTER);
            headerText.add(headerFirstLine);
            headerText.add(headerSecondLine);
            headerText.add(headerThirdLine);
            headerText.add(underLine);

            Cell cell = new Cell();
            cell.add(headerLogo.setAutoScale(true));

            float[] colWidth = { 100f };
            Table logoContainer = new Table(colWidth);
            logoContainer.setHorizontalAlignment(HorizontalAlignment.CENTER);
            logoContainer.addCell(cell.setBorder(Border.NO_BORDER));

            PdfFont containerFont = PdfFontFactory.createFont(TIMES_ROMAN);
            Paragraph firstCellParagraph =
                    getParagraphFirstColumn(getFromDb)
                            .setFont(containerFont)
                            .setFontSize(15f);

            Paragraph secondCellParagraph =
                    getParagraphSecondColumn(getFromDb)
                            .setFont(containerFont)
                            .setFontSize(15f);

            String middleName = getFromDb.getMiddleName() == null ? "" : getFromDb.getMiddleName();
            Paragraph declaration = new Paragraph("Declaration by Applicant: ");
            declaration.add("I `" + getFromDb.getName() + " "
                    + getFromDb.getSurname() + " "
                    + middleName)
                    .setFont(headerFont)
                    .setFontSize(13f);
            declaration.add("` Solemnly declare that the above information given by me are " +
                    "true and that I should be penalised if the information dater found to be false");
            declaration.setMargins(5f, 0f, 3f, 65f);

            Cell firstCell = new Cell();
            firstCell.add(firstCellParagraph);

            Cell secondCell = new Cell();
            secondCell.add(secondCellParagraph);

            float[] columnWidth = { 450f, 350f };
            Table bioContainer = new Table(columnWidth);
            bioContainer.setMargins(0f, 0f, 3f, 45f);
            bioContainer.addCell(firstCell.setBorder(Border.NO_BORDER));
            bioContainer.addCell(secondCell.setBorder(Border.NO_BORDER));

            Image watermarkImg = waterMark(path, pdfDocument);
            Image barcode = barcodeGenerator(pdfDocument);
            barcode.setMarginTop(50f);

            PdfFont signatureFont = PdfFontFactory.createFont(SIGNATURE_FONT);
            Text signature = new Text(".    Usman&Muhammad    \t.");
            signature
                    .setFont(signatureFont)
                    .setUnderline();

            Text villageheadText = new Text("\nSignature of Village Head");
            villageheadText.setFont(headerFont).setFontColor(ColorConstants.RED);

            Paragraph signatureParagraph = new Paragraph(signature);
            signatureParagraph.add(villageheadText);

            Text districtSignature = new Text(".    SaniShehu    .");
            districtSignature
                    .setFont(signatureFont)
                    .setUnderline();

            Text districtHeadText = new Text("\nSignature of District Head");
            districtHeadText
                    .setFont(headerFont)
                    .setFontColor(ColorConstants.RED);

            Paragraph signaturePara = new Paragraph(districtSignature);
            signaturePara.add(districtHeadText);

            Cell firstCellSign = new Cell();
            firstCellSign.add(signatureParagraph);

            Cell secondCellSign = new Cell();
            secondCellSign.add(signaturePara);
            secondCellSign.add(barcode);

            float[] tableColWidth = { 300f, 250f };
            Table signatureTable = new Table(tableColWidth);
            signatureTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            signatureTable.addCell(firstCellSign.setBorder(Border.NO_BORDER));
            signatureTable.addCell(secondCellSign.setBorder(Border.NO_BORDER));


            document.add(logoContainer);
            document.add(watermarkImg);
            document.add(headerText);
            document.add(bioContainer);
            document.add(declaration);
            document.add(signatureTable);

            document.close();
            pdfDocument.close();

            return outputStream;
        }

    }

//    private Paragraph signature() throws IOException {
//        PdfFont signatureFont = PdfFontFactory.createFont(SIGNATURE_FONT);
//    }

    private Image barcodeGenerator(PdfDocument pdfDocument) {
        BarcodeQRCode barcode = new BarcodeQRCode("https://www.youtube.com/watch?v=SZEhv8tpT6U&list=PLFh8wpMiEi8-Yo59DBCasuVi1M29kQrvn&index=1");
        PdfFormXObject barcodeObject = barcode.createFormXObject(ColorConstants.BLACK, pdfDocument);

        return new Image(barcodeObject).setAutoScale(true);
    }

    private Image waterMark(String path, PdfDocument pdfDocument) throws MalformedURLException {
        ImageData img = ImageDataFactory.create(path);
        Image watermarkImage = new Image(img);

        watermarkImage.setFixedPosition((pdfDocument.getDefaultPageSize().getWidth() / 2) - 320,
                (pdfDocument.getDefaultPageSize().getHeight()/ 2) - 320);
        watermarkImage.setOpacity(0.1f);

        return watermarkImage;
    }

    private static Paragraph getParagraphFirstColumn(Indigene getFromDb) {
        Paragraph firstCell = new Paragraph();
        firstCell.add(
                "1.\tName of Applicant:  " + getFromDb.getName() + "\n" +
                "2.\tFather's Name & Tribe:  " + getFromDb.getSurname() + "/" + getFromDb.getFathersTribe() + "\n" +
                "3.\tMother's Name & Tribe:  "  + getFromDb.getMothersName() + "/" + getFromDb.getMothersTribe() + "\n" +
                "4.\tFather's Home Town:  " +  getFromDb.getFathersHomeTown() + "\n" +
                "5.\tMother's Home Town:  "  + getFromDb.getMothersHomeTown() + "\n" +
                "6.\tDistrict:  " + getFromDb.getWardInLocalGovernmentArea()
        );

        return firstCell;
    }

    private static Paragraph getParagraphSecondColumn(Indigene getFromDb) {
        Paragraph secondCell = new Paragraph();
        secondCell.add(
                "7.\tVillage:  " + getFromDb.getVillage() + "\n" +
                        "8.\tPlace Of Birth:  " + getFromDb.getPlaceOfBirth() + "\n" +
                        "9.\tDate Of Birth:  " + getFromDb.getDateOfBirth() + "\n" +
                        "10.  Ward in Local Govern't Area:  " + getFromDb.getWardInLocalGovernmentArea() + "\n" +
                        "11.  State of Origin:  " + getFromDb.getStateOfOrigin() + "\n" +
                        "12.  Nationality:  " + getFromDb.getNationality() + "\n"
        );

        return secondCell;
    }

    @Override
    public List<Indigene> fetchIndigeneInfo() throws NoApplicantFound {
        List<Indigene>  findAllApplicants = indigeneRepository.findAll();

        if (findAllApplicants.isEmpty()) {
            throw new NoApplicantFound("No Applicant Found");
        }
        return findAllApplicants;
    }

    /*@Override
    public Indigene fetchByCertificateNumber(String certificateNumber) throws NoApplicantFound {
        Optional<Indigene> fetchByCertificateNumber = Optional.ofNullable(indigeneRepository.findByCertificateNumber(certificateNumber));

        if (fetchByCertificateNumber.isEmpty()) {
            throw new NoApplicantFound("Application With Certificate Number " + certificateNumber +
                    "Not Found");
        }
        return fetchByCertificateNumber.get();
    }*/

    @Override
    public void updateApplicantInfo(Indigene indigene, Long id) throws FailedToUpdateIndigeneshipApplication {
        Optional<Indigene> updateIndigeneById = indigeneRepository.findById(id);
        Indigene indigeneDb = null;

        if (updateIndigeneById.isEmpty()) {
            throw new FailedToUpdateIndigeneshipApplication("Failed To Update Indigeneship Application");
        } else {
            indigeneDb = updateIndigeneById.get();

            if (Objects.nonNull(indigene.getName()) &&
                    !"".equalsIgnoreCase(indigene.getName())) {
                indigeneDb.setName(indigene.getName());
            }

            if (Objects.nonNull(indigene.getSurname()) &&
                    !"".equalsIgnoreCase(indigene.getSurname())) {
                indigeneDb.setSurname(indigene.getSurname());
            }

            if (Objects.nonNull(indigene.getMiddleName()) &&
                    !"".equalsIgnoreCase(indigene.getMiddleName())) {

                indigeneDb.setMiddleName(indigene.getMiddleName());
            }

            if (Objects.nonNull(indigene.getFathersHomeTown()) &&
                    !"".equalsIgnoreCase(indigene.getFathersHomeTown())) {

                indigeneDb.setFathersHomeTown(indigene.getMiddleName());
            }

            if (Objects.nonNull(indigene.getMothersName()) &&
                    !"".equalsIgnoreCase(indigene.getMothersName())) {

                indigeneDb.setMothersName(indigene.getMothersName());
            }

            if (Objects.nonNull(indigene.getMothersTribe()) &&
                    !"".equalsIgnoreCase(indigene.getMothersTribe())) {

                indigeneDb.setMothersHomeTown(indigene.getMothersHomeTown());
            }

            if (Objects.nonNull(indigene.getMothersTribe()) &&
                    !"".equalsIgnoreCase(indigene.getMothersTribe())) {

                indigeneDb.setMothersTribe(indigene.getMothersTribe());
            }

            if (Objects.nonNull(indigene.getVillage()) &&
                    !"".equalsIgnoreCase(indigene.getVillage())) {

                indigeneDb.setVillage(indigene.getVillage());
            }

            if (Objects.nonNull(indigene.getPlaceOfBirth()) &&
                    !"".equalsIgnoreCase(indigene.getPlaceOfBirth())) {

                indigeneDb.setPlaceOfBirth(indigene.getPlaceOfBirth());
            }

            if (Objects.nonNull(indigene.getDateOfBirth()) &&
                    !"".equalsIgnoreCase(String.valueOf(indigene.getDateOfBirth()))) {

                indigeneDb.setDateOfBirth(indigene.getDateOfBirth());
            }

            if (Objects.nonNull(indigene.getWardInLocalGovernmentArea()) &&
                    !"".equalsIgnoreCase(indigene.getWardInLocalGovernmentArea())) {

                indigeneDb.setWardInLocalGovernmentArea(indigene.getWardInLocalGovernmentArea());
            }

            if (Objects.nonNull(indigene.getStateOfOrigin()) &&
                    !"".equalsIgnoreCase(indigene.getStateOfOrigin())) {

                indigeneDb.setStateOfOrigin(indigene.getStateOfOrigin());
            }

            if (Objects.nonNull(indigene.getNationality()) &&
                    !"".equalsIgnoreCase(indigene.getNationality())) {

                indigeneDb.setNationality(indigene.getNationality());
            }

            indigeneRepository.save(indigeneDb);
        }

    }

    @Override
    public void deleteIndigeneApplication(Long applicantId) throws FailedToDeleteIndigeneshipApplication {
            if (!indigeneRepository.existsById(applicantId)) {
            throw new FailedToDeleteIndigeneshipApplication("No Application Found With ID: " +
                    applicantId + " To Be Deleted");
        }
        indigeneRepository.deleteById(applicantId);
    }

    @Override
    public UserStatus fetchApplicationStatus(Long userId) throws FailedToUpdateIndigeneshipApplication {
        Optional<Indigene> fetchFromDb = indigeneRepository.findById(userId);
        if (fetchFromDb.isEmpty()) {
            throw new FailedToUpdateIndigeneshipApplication("No Application Yet is Submitted");
        }
        return fetchFromDb.get().getApplicationStatus();
    }
}
