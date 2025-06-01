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
	public String songsNoT ;
	public String yearT;

    public Albums(String album, String image ) {//,String artist,String album) {



    	         this.imaget=image;
    	         this.AlbumC=album;
    	     //    this.songsNoT = songNumbers;


	}
    public Albums(String album, String image ,String songsNo ,String year) {//,String artist,String album) {



        this.imaget=image;
        this.AlbumC=album;
    	this.songsNoT = songsNo;
    	this.yearT = year;


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

    public String SongsGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return songsNoT;
    }
    public void songsSet(String songs){
    	songsNoT=songs;
    }

    public String yearGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return yearT;
    }
    public void YearSet(String year){
    	yearT=year;
    }








}

