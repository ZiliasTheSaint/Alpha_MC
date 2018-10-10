package alphaMc.resources;

import java.util.ListResourceBundle;

/**
 * The resource bundle for Alpha class. <br>
 * 
 * @author Dan Fulea, 05 APR. 2011
 * 
 */
public class AlphaResources extends ListResourceBundle {
	/**
	 * Returns the array of strings in the resource bundle.
	 * 
	 * @return the resources.
	 */
	public Object[][] getContents() {
		// TODO Auto-generated method stub
		return CONTENTS;
	}

	/** The resources to be localised. */
	private static final Object[][] CONTENTS = {

			// menu labels...
			//{ "form.icon", "jdf/resources/personal.jpg" },// "gui/resources/personal.jpg"
															// },
			{ "form.icon.url", "/danfulea/resources/personal.png"},//duke.png"},///thumbsup.gif" },// "/gui/resources/personal.jpg"
																// },
			{ "icon.url", "/danfulea/resources/personal.png"},//globe.gif"},//Kerrigan.jpg" },// "/gui/resources/Kerrigan.jpg"
															// },
			//{ "icon", "jdf/resources/Kerrigan.jpg" },// "gui/resources/Kerrigan.jpg"
														// },

			{ "img.chart.bar", "/danfulea/resources/chart_bar.png" },
			{ "img.chart.curve", "/danfulea/resources/chart_curve.png" },
			
			{ "img.zoom.in", "/danfulea/resources/zoom_in.png" },
			{ "img.zoom.out", "/danfulea/resources/zoom_out.png" },
			{ "img.pan.left", "/danfulea/resources/arrow_left.png" },
			{ "img.pan.up", "/danfulea/resources/arrow_up.png" },
			{ "img.pan.down", "/danfulea/resources/arrow_down.png" },
			{ "img.pan.right", "/danfulea/resources/arrow_right.png" },
			{ "img.pan.refresh", "/danfulea/resources/arrow_refresh.png" },

			{ "img.accept", "/danfulea/resources/accept.png" },
			{ "img.insert", "/danfulea/resources/add.png" },
			{ "img.delete", "/danfulea/resources/delete.png" },
			{ "img.delete.all", "/danfulea/resources/bin_empty.png" },
			{ "img.view", "/danfulea/resources/eye.png" },
			{ "img.set", "/danfulea/resources/cog.png" },
			{ "img.report", "/danfulea/resources/document_prepare.png" },
			{ "img.today", "/danfulea/resources/time.png" },
			{ "img.open.file", "/danfulea/resources/open_folder.png" },
			{ "img.open.database", "/danfulea/resources/database_connect.png" },
			{ "img.save.database", "/danfulea/resources/database_save.png" },
			{ "img.substract.bkg", "/danfulea/resources/database_go.png" },
			{ "img.close", "/danfulea/resources/cross.png" },
			{ "img.about", "/danfulea/resources/information.png" },
			{ "img.printer", "/danfulea/resources/printer.png" },
			
			{ "Application.NAME", "Alpha_MC: Alpha detector efficiency" },

			{ "Author", "Author:" },
			{ "Author.name", "Dan Fulea , fulea.dan@gmail.com" },

			{ "Application.name", "Alpha_MC" },
			{ "Version", "Version:" },
			{ "Version.name", "Alpha_MC 1.0" },

			{
					"License",
					//"This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License (version 2) as published by the Free Software Foundation. \n\nThis program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. \n" },
			"Copyright (c) 2014, Dan Fulea \nAll rights reserved.\n\nRedistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:\n\n1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.\n\n2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.\n\n3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 'AS IS' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" },
			{ "pleaseWait.label", "Work in progress!" },

			{ "mainPanel.textArea.label", "Results:" },

			{ "menu.file", "File" },
			{ "menu.file.mnemonic", new Character('F') },

			{ "menu.help.LF", "Look and feel..." },
			{ "menu.help.LF.mnemonic", new Character('L') },
			{ "menu.help.LF.toolTip", "Change application look and feel" },
			
			{ "runB", "Run" },
			{ "runB.toolTip", "Application start" },
			{ "runB.mnemonic", new Character('R') },

			{ "killB", "Kill" },
			{ "killB.toolTip", "Application stop" },
			{ "killB.mnemonic", new Character('K') },

			{ "printB", "Pdf print..." },
			{ "printB.toolTip", "Save report as pdf file" },
			{ "printB.mnemonic", new Character('P') },

			{
					"numberOfHystoriesCb",
					new String[] { "1000", "10000", "20000", "40000", "80000",
							"150000", "300000", "1000000" } },

			{ "menu.file.exit", "Close" },
			{ "menu.file.exit.mnemonic", new Character('C') },
			{ "menu.file.exit.toolTip", "Close the application" },

			{ "menu.help", "Help" },
			{ "menu.help.mnemonic", new Character('H') },

			{ "menu.help.about", "About..." },
			{ "menu.help.about.mnemonic", new Character('A') },
			{ "menu.help.about.toolTip",
					"Several informations about this application" },

			{ "numberOfHystoriesLb", "Number of histories: " },
			{ "sourceToDetectorDistanceLb", "Source-Detector distance [cm]: " },
			{ "sourceDiameterLb", "Source diameter [cm]: " },
			{ "detectorDiameterLb", "Detector diameter [cm]: " },

			{ "text.simulation.stop", "Interrupred by user!" },

			{ "status.wait", "Waiting for your action!" },
			{ "status.computing", "Computing..." },
			{ "status.done", "All done! " },
			{ "status.error", "Unexpected error!" },
			{ "status.save", "Saved: " },
			{ "number.error", "Insert valid positive numbers! " },

			{ "text.alphaEff", "Alpha efficiency [/100 particles] = " },
			{ "text.alphaEff.err", " ; 2 sigma uncertainty [%] = " },
			{ "text.solidAngle", "Geometry solid angle [sr] = " },
			{ "text.solidAngle.err", " ; 2 sigma uncertainty [%] = " },

			{ "dialog.exit.title", "Confirm..." },
			{ "dialog.exit.message", "Are you sure?" },
			{ "dialog.exit.buttons", new Object[] { "Yes", "No" } },

			{ "pdf.metadata.title", "Alpha_MC PDF" },
			{ "pdf.metadata.subject", "MC-simulation results" },
			{ "pdf.metadata.keywords", "Alpha_MC, PDF" },
			{ "pdf.metadata.author", "Alpha_MC" },
			{ "pdf.content.title", "Alpha_MC Simulation Report" },
			{ "pdf.content.subtitle", "Report generated by: " },
			{ "pdf.page", "Page " },
			{ "pdf.header", "Alpha_MC output" },
			{ "file.extension", "pdf" },
			{ "file.description", "PDF file" },
			{ "dialog.overwrite.title", "Overwriting..." },
			{ "dialog.overwrite.message", "Are you sure?" },
			{ "dialog.overwrite.buttons", new Object[] { "Yes", "No" } },

			{ "alpha.settings",
					"Simulation settings for alpha detector-source geometry:" },
			{ "alpha.results", "Simulation results:" },

	};

}
