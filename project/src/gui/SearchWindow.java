package gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class SearchWindow extends JFileChooser{
	private static final long serialVersionUID = -8766109248469633410L;
	private String path = null;
	
	public SearchWindow(String name) {
		super(FileSystemView.getFileSystemView().getHomeDirectory());
		
		this.setDialogTitle(name);
		this.setAcceptAllFileFilterUsed(false);
		this.addChoosableFileFilter(
				new FileNameExtensionFilter("Text Files", "txt"));
		
		int result = this.showDialog(null, "Pick File");
		if(result == JFileChooser.APPROVE_OPTION)
			path = this.getSelectedFile().getAbsolutePath();
	}

	public String getPath() {
		return path;
	}
}
