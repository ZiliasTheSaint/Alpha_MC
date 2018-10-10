package alphaMc.resources;

import java.util.ListResourceBundle;

/**
 * The resource bundle for PleaseWait class. <br>
 * 
 * @author Dan Fulea, 25 JAN. 2004
 */
public class MessageResources extends ListResourceBundle {
	/**
	 * Returns the array of strings in the resource bundle.
	 * 
	 * @return the resources.
	 */
	public Object[][] getContents() {
		return CONTENTS;
	}

	/** The resources to be localised. */
	private static final Object[][] CONTENTS = {
			//{ "form.icon", "jdf/resources/personal.jpg" },// "gui/resources/personal.jpg"
															// },
			{ "form.icon.url", "/danfulea/resources/personal.png"},//"/jdf/resources/duke.png"},//personal.jpg" },// "/gui/resources/personal.jpg"
																// },
			{ "plswait.title", "Please wait!" },
			{ "plswait.label", "Work in progres..." }, };

}
