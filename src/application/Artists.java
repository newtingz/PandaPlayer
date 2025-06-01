package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.concurrent.Task;
import javafx.scene.image.ImageView;

@SuppressWarnings("unused")




public class Artists {

	//private String titleC;
	private String artistC;
	//private String composerC;
	//private String GenreC;
	private String AlbumC;
	public String absolutePatht;

	public String namet ;
	public String imaget;

    public Artists(String Artist, String image) {//,String artist,String album) {



    	         this.imaget=image;
    	         this.artistC=Artist;


	}



    public String artistGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return artistC;
    }
    public void setArtist(String album){
    	artistC=album;
    }
    public String ImageGet(){
    	//System.out.println("get location =============="+namet +absolutePatht);
    	return imaget;
    }
    public void setImage(String image){
    	imaget=image;
    }






}

