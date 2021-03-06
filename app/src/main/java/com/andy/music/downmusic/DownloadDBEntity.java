package com.andy.music.downmusic;

/**
 *
 * Created by Andy on 2017/7/6.
 */

public class DownloadDBEntity {
    private String downloadId;
    private Long toolSize;
    private Long completedSize;
    private String url;
    private String saveDirPath;
    private String fileName;
    private String artist;
    private int downloadStatus;

    public DownloadDBEntity() {
    }

    public DownloadDBEntity(String downloadId) {
        this.downloadId = downloadId;
    }

    public DownloadDBEntity(String downloadId, Long toolSize, Long completedSize, String url, String saveDirPath, String fileName, String artist, Integer downloadStatus) {
        this.downloadId = downloadId;
        this.toolSize = toolSize;
        this.completedSize = completedSize;
        this.url = url;
        this.saveDirPath = saveDirPath;
        this.fileName = fileName;
        this.artist = artist;
        this.downloadStatus = downloadStatus;
    }

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public Long getTotalSize() {
        return toolSize;
    }

    public void setToolSize(Long toolSize) {
        this.toolSize = toolSize;
    }

    public Long getCompletedSize() {
        return completedSize;
    }

    public void setCompletedSize(Long completedSize) {
        this.completedSize = completedSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSaveDirPath() {
        return saveDirPath;
    }

    public void setSaveDirPath(String saveDirPath) {
        this.saveDirPath = saveDirPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Integer downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
