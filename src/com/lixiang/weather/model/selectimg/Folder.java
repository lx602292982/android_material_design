package com.lixiang.weather.model.selectimg;

import java.util.List;

/**
 * 文件夹
 * Created by Nereo on 2015/4/7.
 */
public class Folder {
    public String name;
    public String path;
    public Image cover;
    public List<Image> images;
    
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public Image getCover() {
		return cover;
	}


	public void setCover(Image cover) {
		this.cover = cover;
	}


	public List<Image> getImages() {
		return images;
	}


	public void setImages(List<Image> images) {
		this.images = images;
	}


	@Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
