package fxTabs;

import classes.Craftable;
import classes.Craftref;
import classes.Enchref;
import classes.Gearset;
import classes.Iref;
import classes.Item;
import classes.Items;
import interfaces.fxEditItem;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import vars.GearSlot;

public class Gearsets {

	private static Tab tab;

	private static GridPane grid;

	public static Gearset currentGearset;

	public static Tab getTab() {
		tab = new Tab("Gearsets");

		currentGearset = new Gearset();

		BorderPane content = new BorderPane();
		content.setCenter(gridContent());
		content.setOnKeyPressed(key -> {
			if(key.getCode() == KeyCode.F5) updateContent();
		});

		// TODO build the gearset into the build class

		ScrollPane scrollContent = new ScrollPane();
		scrollContent.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollContent.setContent(content);

		tab.setContent(scrollContent);

		return tab;
	}

	public static void setGearset(Gearset gearset) {
		currentGearset = gearset;
	}

	public static GridPane gridContent() {
		/*
		 * Setup:
		 * name | enchantments? | crafting (choices) | item set ? | [choose] | [delete]
		 */

		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		updateContent();

		return grid;
	}

	public static void updateContent() {
		grid.getChildren().clear();

		Text[] headers = {new Text("Slot"), new Text(""), new Text("Name"), new Text("Enchantments"), new Text("Crafting"), new Text("Set")};

		// Headers
		grid.addRow(0, headers);

		// per slot:
		int row = 1;

		for(GearSlot slot : GearSlot.values()) {
			Iref ref = currentGearset.getItemBySlot(slot);

			ImageView icon = (ref != null && ref.getItem().getIconViewSmall() != null) ? ref.getItem().getIconViewSmall() : new ImageView();

			Text name = new Text();
			name.setText((ref != null) ? ref.getItem().getName() : "");
			name.setOnMouseClicked(click -> {

				if(click.getClickCount() == 2) {
					fxEditItem.open(ref);
				}

			});

			Text enchantments = new Text();
			if(ref != null) for(Enchref e : ref.getItem().getEnchantments()) enchantments.setText(enchantments.getText() + e.getDisplayName() + "\n");

			Button bSelect = new Button("Select " + slot.displayName() + "...");
			bSelect.setOnAction(e -> {
				Item i = Items.selectItemPrompt(slot.getItemSlot());

				if(i != null) {
					currentGearset.setItemBySlot(i, slot);
					updateContent();
				}

			});

			VBox craftingChoices = new VBox();
			craftingChoices.setSpacing(10);

			if(ref != null && ref.getCrafting() != null) {

				for(Craftref cref : ref.getCrafting()) {
					HBox h = new HBox(new Text(ref.getItem().getCraft(cref.getUUID()).getName()), new craftingChoice(ref, cref).toComboBox());
					h.setSpacing(2.5);
					craftingChoices.getChildren().add(h);
				}

			}

			Button bClear = new Button("Clear");
			bClear.setOnAction(e -> {
				currentGearset.setItemBySlot((Iref) null, slot);
				updateContent();
			});

			grid.addRow((row++), new Text(slot.displayName()), icon, name, enchantments, craftingChoices, bSelect, bClear);
		}

	}

	private static class craftingChoice extends ComboBox<Enchref> {

		private Craftref ref;
		private Craftable craftable;

		public craftingChoice(Iref iref, Craftref ref) {
			super();
			this.ref = ref;
			this.craftable = iref.getItem().getCraft(ref.getUUID());
		}

		public ComboBox<Enchref> toComboBox() {
			this.getItems().addAll(craftable.getChoices());
			this.getSelectionModel().select(craftable.getChoice(ref.getIndex()));
			this.getSelectionModel().selectedIndexProperty().addListener((e, o, n) -> ref.setIndex(n.intValue()));

			return this;
		}
	}
}
