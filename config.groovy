// Configuration file for ACOA-APECA
normal = 0.5;
error = 0.03;
confidence = 0.95;
archive = 0; // 1:both, 2:non, 3:archive
archiveCol = 3;
names = [];
sampleSize = 2500;
numOfUsers = 5;
pagesPerUser = 100;


institutionNameProperty = "acoa-apeca"
secondaryName = "-"

properNameProperty = "ACOA-APECA - Atlantic Canada Opportunities Agency"

baseStreamFolderName = "C:/Users/Khama/Documents/GitHub/Sample-And-Assign/src/"
baseWaveName = baseStreamFolderName
baseFolderName = baseWaveName

// siteQuery = "institutionSite:ACOA-APECA\\ -\\ Atlantic\\ Canada\\ Opportunities\\ Agency";
siteQuery = "Institution:ACOA-APECA AND Site:Atlantic\\ Canada\\ Opportunities\\ Agency";

// Maximum number of rows in one content inventory spreadsheet
ciMaxRowsProperty = 25000;

// Used to create the content map, etc.
contentMapCsvFileNameProperty = baseFolderName + institutionNameProperty + secondaryName + "content_inventory.csv"
// contentMapQueryProperty = siteQuery + " AND ci.uri:business AND HTTP-Response-Code:200 AND ci.Content-Type:html AND -uri:https* AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive"
// originalContentMapQueryProperty = siteQuery + " AND ci.uri:business AND isOriginal:true AND HTTP-Response-Code:200 AND ci.Content-Type:html AND -uri:https* AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive"
contentMapQueryProperty = siteQuery + " AND HTTP-Response-Code:200 AND ci.Content-Type:html AND -uri:https* AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive"
originalContentMapQueryProperty = siteQuery + " AND isOriginal:true AND HTTP-Response-Code:200 AND ci.Content-Type:html AND -uri:https* AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive"

// Used to modify the title in the content map.
titleMapCsvFileNameProperty = baseFolderName + institutionNameProperty + secondaryName + "title_map.csv"
def mapTitleProperty = false

// Used to generate the complete list of assets
assetListCsvFileNameProperty = baseFolderName + institutionNameProperty + secondaryName + "asset_inventory.csv"
assetListQueryProperty = siteQuery + " AND HTTP-Response-Code:200 AND sha1:* AND -ci.Content-Type:html AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive"
originalAssetListQueryProperty = siteQuery + " AND isOriginal:true AND sha1:* AND HTTP-Response-Code:200 -ci.Content-Type:html AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive"

// Used to generate the complete list of page URLs.
urlListCsvFileNameProperty = baseFolderName + institutionNameProperty + secondaryName + "URLs.csv"
urlListQueryProperty = siteQuery + " AND -uri:*\\/ AND -ci.uri:proactivedisclosure AND -ci.uri:divulgationproactive AND -ci.uri:https AND HTTP-Response-Code:200 AND ci.Content-Type:html"

// Used for the removal of stopwords from the automatically generated keywords.
stopwordsFileNameProperty = baseWaveName + "stopwords.txt"

// Does the site use a case insensitive URL?
caseInsensitiveUrlFlagProperty = true

// Allow the Discovered Language from the URL to over-ride user entered metadata.  This should normally be false.  Only in rare occassions will
// it be set to true.
prioritizeLanguageURLProperty = false;

useAltLangTagProperty = true
altLangSelectionProperty = "li.fiptexta a"
useURLLangMapProperty = false
urlLangMapEnProperty = []
urlLangMapFrProperty = []

// Use to output the zip container containing the entire migration.
migrationContainerFileNameProperty = baseFolderName + institutionNameProperty + secondaryName + "out.zip"

flatTitleListProperty =	["^acoa-",
                         "^apeca-"]

socialMediaVariableMapProperty = ["twitter":"vrTwtt1",
                                  "youtube":"vrYtub1"]

blacklistSelectorsProperty = ["footer",
                              "script",
                              "div.clear"]

destinationBlacklistProperty = ["^http://acoa-apeca.gc.ca",
                                "^http://www.acoa-apeca.gc.ca",
                                "^http://apeca-acoa.gc.ca",
                                "^http://www.apeca-acoa.gc.ca",
                                "^http://mediaroom.acoa-apeca.gc.ca",
                                "\\.shtml",
                                "\\.html",
                                "\\.htm",
                                "\\.aspx",
                                "\\.thmx",
                                "-eng",
                                "-fra",
                                "/e/",
                                "/f/",
                                "/eng/",
                                "/fra/",
                                "/en/",
                                "/fr/"]

// The following are regular expressions
englishUrlLangProperties = [".*(-|_)(?iu)(eng|en|e)\\..*", ".*=(?iu)(en|eng|english).*", ".*(?iu)(lang|lng)=(e|en|eng|english).*", ".*/(?iu)(eng|en|e|english)/.*"]
frenchUrlLangProperties = [".*(-|_)(?iu)(fra|fre|fr|f)\\..*", ".*=(?iu)(fr|fra|fre|french|francais).*", ".*(?iu)(lang|lng)=(f|fr|fra|fre|french|francais).*", ".*/(?iu)(fra|fre|fr|f|french|francais)/.*"]

bodySelectorProperty = "div.center";
sitenameProperty = "atlantic-canada-opportunities"
sitenamefrProperty = "promotion-economique-canada-atlantique"

titleSelectorProperty = "h1"

// Use Rules-Based Short Name
rulesBasedShortNameProperty = "atlantic-canada-opportunities"
rulesBasedShortNameFrProperty = "promotion-economique-canada-atlantique"
// Unless there is an Alternative Short Name
alternativeShortNameProperty = "atlantic-canada-opportunities"
alternativeShortNameFrProperty = "promotion-economique-canada-atlantique"

creatorProperty = "{Government of Canada, Atlantic Canada Opportunities Agency}{Gouvernement du Canada, Agence de promotion �conomique du Canada atlantique}"
subjectProperty = "{Atlantic Canada Opportunities}{�conomique du Canada atlantique}"
breadcrumbsProperty = [["Atlantic Canada Opportunities Agency", "/en/" + sitenameProperty + "/index.html"]];
breadcrumbsFrProperty = [["Agence de promotion �conomique du Canada atlantique", "/fr/" + sitenamefrProperty + "/index.html"]];
socialMediaEnProperty = [["twitter", "https://twitter.com/acoacanada"],
                         ["youtube", "http://www.youtube.com/acoacanada"]];
socialMediaFrProperty = [["twitter", "https://twitter.com/apecacanada"],
                         ["youtube", "http://www.youtube.com/apecacanada"]];

// List of information to remove from page titles
titleBlackListProperty = ["ACOA \\|", "APECA \\|"];

// Information used for various mappings provided by the Institution in spreadsheets - NOTE: We probably need to have multiples of these for each 'attribute'
// such as archived pages, page types, topics, themes, etc.  They may all be in the same file or sheets, but because they can occur separately we need
// to treat each one individually.
excelFileNameProperty = baseWaveName + "institution_mappings/ACOA_WRI Content Inventory Spreadsheet Template FINAL - Aug21.xlsx";
sheetNameProperty = "Content Inventory (Bilingual)";
headerRowIndexProperty = 0; // The row where the headers for columns is located.  Index of this starts at 0 so in fact this is the 1st row in the sheet.
uriColumnProperty = 0; // The column containing the URL
pageStatusColumnProperty = "G"; // The column containing an indicator which determines if the page Index of this starts at 0 so in fact this is the 7th column or column G in the sheet.
pageTypeColumnProperty = "H";
primaryTopicColumnsProperty  = []; // The columns containing all the primary topic information.  An empty array would indicate that there are no primary topics.
secondaryTopicColumnsProperty  = []; // The columns containing all the secondary topic information.  An empty array would indicate that there are no secondary topics.
themeColumnsProperty  = []; // The columns containing all the theme information.  An empty array would indicate that there is no theme.

// This is all the information required for the Excel spreadsheet for the Content Inventory.
ciTemplateFileNameProperty = baseStreamFolderName + "WRI_Content_Inventory_Template_Final.xlsx";
ciOutFileNameProperty = baseFolderName + "ACOA-APECA-Content_Inventory_Spreadsheet";
ciSheetNameProperty = "Onboarding Content Inventory";

// Columns to be used for migration.   NOTE: These may be different from the mapping ones above and thus, must be
// different names.  The defaults for these will be setup in the Governor migration script and they will map to the
// default content inventory template.  Any columns which do not map MUST be defined here.
// ciFiles = [baseFolderName + "ACOA-APECA-Content_Inventory_Spreadsheet-RowNum_1-9107.xlsx"];
ciFiles = [baseFolderName + "PHAC-test-ci.xlsx"];
ciSheetName = "Onboarding Content Inventory";
ciHeaderRowIndex = 1;
ciSha1Col = "X";
ciPrimaryLanguageCol = "A";
ciPrimaryURLCol = "B";
ciAltLangURLCol = "C";
ciMWSDestCol = "E";
ciStorageIACol = "D";
ciPrimaryTitleCol = "G";
ciAlternateLanguageTitleCol = "H";
ciDescriptionCol = "I";
ciPageStatusCol = "J";
ciPageTypeCols = ["K","L"];
ciAudienceCol = "M";
ciTopicCols = ["N","O"];
// ciContactUsCol = "P";
ciBranchProgramCol = "P";
ciCommentsCol = "Q";

// This is the file that is used to VALIDATE the controlled vocabulary.  It should be the SAME for every single site.
cvFileNameProperty = baseStreamFolderName + "controlled-vocabulary-config.groovy";
componentToJsonFileNameProperty = baseStreamFolderName + "migration/scripts/JcrComponents.groovy";

// This is used to determine if we are going to attempt to generate columns during the migration...
// Empty values or not having them indicates that we won't be using columns.  Multiple
// selectors can be used to cover more cases for a site but only the first one found will be
// used.
leftColSelectorProperty = "div.left";
centerColSelectorProperty = "div.center";
// leftColSelectorProperty = "";
// centerColSelectorProperty = "";
