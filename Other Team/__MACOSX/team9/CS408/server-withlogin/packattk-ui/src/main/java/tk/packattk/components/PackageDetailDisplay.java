package tk.packattk.components;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class PackageDetailDisplay extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private GridLayout gridLayout_1;
	@AutoGenerated
	private Label admin;
	@AutoGenerated
	private Label destination;
	@AutoGenerated
	private Label location;
	@AutoGenerated
	private Label timestamp;
	@AutoGenerated
	private Label tracking;
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PackageDetailDisplay() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	public void displayPackage(tk.packattk.utils.Package p) {
		tracking.setValue(p.getTracking());
		location.setValue(p.getLocation());
		destination.setValue(p.getDestination());
		
		if (p.getAdmin() != null) {
			admin.setValue(p.getAdmin().getFirstName() + " "
					+ p.getAdmin().getLastName());
		} else {
			admin.setValue("Unknown");
		}
		
		Date scanTime = new Date(p.getTime());
		timestamp.setValue(DateFormat.getInstance().format(scanTime));
	}

	public void clearDisplay() {
		tracking.setValue(" ");
		timestamp.setValue(" ");
		location.setValue(" ");
		destination.setValue(" ");
		admin.setValue(" ");
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// gridLayout_1
		gridLayout_1 = buildGridLayout_1();
		mainLayout.addComponent(gridLayout_1, "top:0.0px;left:0.0px;");

		return mainLayout;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_1() {
		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("-1px");
		gridLayout_1.setHeight("-1px");
		gridLayout_1.setMargin(false);
		gridLayout_1.setSpacing(true);
		gridLayout_1.setColumns(2);
		gridLayout_1.setRows(5);

		// tracking
		tracking = new Label();
		tracking.setCaption("Tracking Number");
		tracking.setImmediate(false);
		tracking.setWidth("-1px");
		tracking.setHeight("-1px");
		tracking.setValue(" ");
		gridLayout_1.addComponent(tracking, 0, 0);

		// timestamp
		timestamp = new Label();
		timestamp.setCaption("Scanned At");
		timestamp.setImmediate(false);
		timestamp.setWidth("-1px");
		timestamp.setHeight("-1px");
		timestamp.setValue(" ");
		gridLayout_1.addComponent(timestamp, 0, 1);

		// location
		location = new Label();
		location.setCaption("Location");
		location.setImmediate(false);
		location.setWidth("-1px");
		location.setHeight("-1px");
		location.setValue(" ");
		gridLayout_1.addComponent(location, 0, 2);

		// destination
		destination = new Label();
		destination.setCaption("Destination");
		destination.setImmediate(false);
		destination.setWidth("-1px");
		destination.setHeight("-1px");
		destination.setValue(" ");
		gridLayout_1.addComponent(destination, 0, 3);

		// admin
		admin = new Label();
		admin.setCaption("Scanned By");
		admin.setImmediate(false);
		admin.setWidth("-1px");
		admin.setHeight("-1px");
		admin.setValue(" ");
		gridLayout_1.addComponent(admin, 0, 4);

		return gridLayout_1;
	}

}
