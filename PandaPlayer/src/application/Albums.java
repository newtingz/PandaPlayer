package application;

public class Albums {

	//private String titleC;
	//private String artistC;
	//private String composerC;
	//private String GenreC;
	private String AlbumC;
	public String absolutePatht;

	public String namet ;
	public String imaget;

    public Albums(String album, String image) {//,String artist,String album) {



    	         this.imaget=image;
    	         this.AlbumC=album;


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






}

