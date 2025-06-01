package application;

@SuppressWarnings("unused")




public class AudioParser {

	//private String titleC;
	Long datemode;
	private String artistC;
	//private String composerC;
	//private String GenreC;
	private String AlbumC;
	public String absolutePatht;

	public String namet ;
	public String imaget;
	public String numberget;
	public String lengthget;
	public String dateModifiedd;
	//public String albumt;

	//File x;


//	public AudioParser(String name,String fileLocation) {
//		this.x=x;
////	    this.name = name;
////	    //AudioParser.artistC = artistC;
////	    this.fileLocation = fileLocation;
//
//	}
	//ADD TRACK LENGTH AND TRACK NUMBER
//    public AudioParser(String name, String absolutePath,String image,String album,String artist) {//,String artist,String album) {
//
//    	    	this.namet = name;
//    	         this.absolutePatht = absolutePath;
//    	         this.imaget=image;
//    	         this.AlbumC=album;
//    	         this.artistC=artist;
//    	//System.out.println("bon suar =============="+name );
//
//
//
//         ///this.AlbumC=album;
//	}
    public AudioParser(String name, String absolutePath,String image,String album,String artist,String albumnun,String Length,String datemod) {//,String artist,String album) {
    	this.dateModifiedd = datemod;
    	this.namet = name;
         this.absolutePatht = absolutePath;
         this.imaget=image;
         this.AlbumC=album;
         this.artistC=artist;
         this.numberget=albumnun;
     	 this.lengthget=Length;
//System.out.println("bon suar =============="+name );



 ///this.AlbumC=album;
}
    public AudioParser(String filename){


    	 //AudioParser.name = name;

	}
    public AudioParser() {

   	 //AudioParser.name = name;

	}
	public String nameGet(){
		//System.out.println("get name =============="+namet );
    	return namet;

    }
	public void setName(String name){
    	namet=name;
    	//System.out.println("set name =============="+name );
    }

	public Long getDatemodedlast(){
		//System.out.println("get name =============="+namet );
		//datemode=null;
//		try {Long.parseLong(dateModifiedd);}catch(NumberFormatException majg) {
//
//
//		}catch(NullPointerException majg) {
//
//			//continue;
//		}

	//	System.out.println(dateModifiedd);

		dateModifiedd= "2021" ;
    	return Long.parseLong(dateModifiedd);

    }


	public String lengthGet(){
		//System.out.println("get name =============="+namet );
    	return lengthget;

    }
	public void lengthSet(String albumnun){
		lengthget=albumnun;
    	//System.out.println("set name =============="+name );
    }

	Long numerator=null;
	String[] numbers= null;
	public Long AlbumnoGet(){
		//System.out.println("get name =============="+namet );
		if(numberget.contains("/")) {
			numbers = numberget.split("/");
			numerator =Long.parseLong(numbers[0]);
		}else {

			numerator =Long.parseLong(numberget);
		}


		 //  numerator = Integer.parseInt(numbers[0]);
    	return numerator ;

    }
	public void AlbumNoSet(String name){
		numberget=name;
    	//System.out.println("set name =============="+name );
    }

    public String fileLocationGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return absolutePatht;
    }
    public void setFileLocation(String absolutePath){
    	absolutePatht=absolutePath;
    }


    public String artistGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return artistC;
    }
    public void setArtist(String artist){
    	artistC=artist;
    }

    public String albumGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return AlbumC;
    }
    public void setAlbum(String album){
    	AlbumC=album;
    }
    public String ImageGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return imaget;
    }
    public void setImage(String image){
    	imaget=image;
    }

public String pathfinder1(){
	return absolutePatht;

	}
//    public static void main(String[] args) {
//    	nameGet();
//    	setName(name);
//    	fileLocationGet();
//    	setFileLocation();
//	}


//	public AudioParser(String fileLocation){
//    	this.fileLocation = fileLocation;


//    public static void main(String[] args) {
//        String fileLocation = null;
//
//        try {
//
//        InputStream input = new FileInputStream(new File(fileLocation));
//        ContentHandler handler = new DefaultHandler();
//        Metadata metadata = new Metadata();
//        Parser parser = new Mp3Parser();
//        ParseContext parseCtx = new ParseContext();
//        parser.parse(input, handler, metadata, parseCtx);
//        input.close();
//
//        // List all metadata
//        String[] metadataNames = metadata.names();
//
//        for(String name : metadataNames){
//        System.out.println(name + ": " + metadata.get(name));
//        }
//        titleC=metadata.get("title");
//    	artistC=metadata.get("xmpDM:artist");
//    	composerC=metadata.get("xmpDM:composer");
//    	GenreC=metadata.get("xmpDM:genre");
//    	AlbumC=metadata.get("xmpDM:album");
//
//        } catch (FileNotFoundException e) {
//        e.printStackTrace();
//        } catch (IOException e) {
//        e.printStackTrace();
//        } catch (SAXException e) {
//        e.printStackTrace();
//        } catch (TikaException e) {
//        e.printStackTrace();
//        }
//        }



//        public static String titleCget(){
//        	return titleC;
//        }
//        public static String artistCget(){
//        	return artistC;
//        }
//        public String composerCGet(){
//        	return composerC;
//        }
//        public String GenreCGet(){
//        	return GenreC;
//        }





}

