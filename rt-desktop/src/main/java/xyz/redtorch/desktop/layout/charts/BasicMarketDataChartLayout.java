package xyz.redtorch.desktop.layout.charts;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import xyz.redtorch.common.constant.CommonConstant;
import xyz.redtorch.common.util.CommonUtils;
import xyz.redtorch.common.util.UUIDStringPoolUtils;
import xyz.redtorch.desktop.service.ChartsDataService;
import xyz.redtorch.pb.CoreEnum.BarCycleEnum;

@Component
public class BasicMarketDataChartLayout {

	private static final Logger logger = LoggerFactory.getLogger(BasicMarketDataChartLayout.class);

	@Autowired
	private ChartsDataService chartsDataService;

	@Autowired
	private Environment environment;

	public BasicMarketDataChart openBasicMarketDataChartWindow(Window parentWindow) {
		return new BasicMarketDataChart(parentWindow);
	}

	public class BasicMarketDataChart {
		private Stage basicMarketDataChartStage = new Stage();
		private VBox basicMarketDataChartVBox = new VBox();
		private Scene scene = new Scene(basicMarketDataChartVBox, 800, 600);
		private String key = UUIDStringPoolUtils.getUUIDString();

		private String chartType = BarCycleEnum.B_1Min.getValueDescriptor().getName();

		private int volumeBarSize = 1000;

		public BasicMarketDataChart(Window parentWindow) {

			scene.getStylesheets().add("main.css");
			basicMarketDataChartStage.setScene(scene);
			basicMarketDataChartStage.setTitle("通用图表");
			basicMarketDataChartStage.initModality(Modality.NONE);
			basicMarketDataChartStage.initOwner(parentWindow);
			basicMarketDataChartStage.show();

			HBox menuHBoxLine1 = new HBox();

			ToggleGroup chartTypeToggleGroup = new ToggleGroup();

			Insets insets = new Insets(2, 5, 2, 5);

			RadioButton bar5sRadioButton = new RadioButton("5s");
			RadioButton bar1MinRadioButton = new RadioButton("1m");
			RadioButton bar3MinRadioButton = new RadioButton("3m");
			RadioButton bar5MinRadioButton = new RadioButton("5m");
			RadioButton bar15MinRadioButton = new RadioButton("15m");
			RadioButton bar1DayRadioButton = new RadioButton("1D");
			RadioButton tickRadioButton = new RadioButton("Tick");
			RadioButton volOPIDistributionRadioButton = new RadioButton("成交开平分布");
			RadioButton volumeBarRadioButton = new RadioButton("量");

			bar5sRadioButton.setUserData(BarCycleEnum.B_5Sec.getValueDescriptor().getName());
			bar1MinRadioButton.setUserData(BarCycleEnum.B_1Min.getValueDescriptor().getName());
			bar3MinRadioButton.setUserData(BarCycleEnum.B_3Min.getValueDescriptor().getName());
			bar5MinRadioButton.setUserData(BarCycleEnum.B_5Min.getValueDescriptor().getName());
			bar15MinRadioButton.setUserData(BarCycleEnum.B_15Min.getValueDescriptor().getName());
			bar1DayRadioButton.setUserData(BarCycleEnum.B_1Day.getValueDescriptor().getName());
			tickRadioButton.setUserData("TICK");
			volOPIDistributionRadioButton.setUserData("VOL_OPI_CHANGE_DISTRIBUTION");
			volumeBarRadioButton.setUserData("VOLUME_BAR");

			bar5sRadioButton.setToggleGroup(chartTypeToggleGroup);
			bar1MinRadioButton.setToggleGroup(chartTypeToggleGroup);
			bar3MinRadioButton.setToggleGroup(chartTypeToggleGroup);
			bar5MinRadioButton.setToggleGroup(chartTypeToggleGroup);
			bar15MinRadioButton.setToggleGroup(chartTypeToggleGroup);
			bar1DayRadioButton.setToggleGroup(chartTypeToggleGroup);
			tickRadioButton.setToggleGroup(chartTypeToggleGroup);
			volOPIDistributionRadioButton.setToggleGroup(chartTypeToggleGroup);
			volumeBarRadioButton.setToggleGroup(chartTypeToggleGroup);

			bar1MinRadioButton.setSelected(true);

			bar5sRadioButton.setPadding(insets);
			bar1MinRadioButton.setPadding(insets);
			bar3MinRadioButton.setPadding(insets);
			bar5MinRadioButton.setPadding(insets);
			bar15MinRadioButton.setPadding(insets);
			bar1DayRadioButton.setPadding(insets);
			tickRadioButton.setPadding(insets);
			volOPIDistributionRadioButton.setPadding(insets);
			volumeBarRadioButton.setPadding(insets);

			TextField volumeBarSizeTextField = new TextField(volumeBarSize + "");
			volumeBarSizeTextField.setPrefWidth(80);
			volumeBarSizeTextField.setDisable(true);
			volumeBarSizeTextField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					String volumeBarSizeString = newValue;
					if (!newValue.matches("\\d*")) {
						volumeBarSizeString = newValue.replaceAll("[^\\d]", "");
						volumeBarSizeTextField.setText(volumeBarSizeString);
					}
					if (StringUtils.isBlank(volumeBarSizeString)) {
						volumeBarSize = 0;
					} else {
						volumeBarSize = Integer.valueOf(volumeBarSizeString);
					}
				}
			});

			chartTypeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					chartType = (String) newValue.getUserData();
					if (chartType.equals("VOLUME_BAR")) {
						volumeBarSizeTextField.setDisable(false);
					} else {
						volumeBarSizeTextField.setDisable(true);
					}
				}
			});

			menuHBoxLine1.getChildren().addAll(bar5sRadioButton, bar1MinRadioButton, bar3MinRadioButton, bar5MinRadioButton, bar15MinRadioButton, bar1DayRadioButton, tickRadioButton,
					volOPIDistributionRadioButton, volumeBarRadioButton, volumeBarSizeTextField);

			HBox menuHBoxLine2 = new HBox();

			LocalDateTime startLocalDateTime = LocalDateTime.now().withMinute(0).withHour(0).withSecond(0).withNano(0);
			LocalDateTime endLocalDateTime = startLocalDateTime.plusDays(1).minusNanos(1);

			TextField unifiedSymbolSymbolTextField = new TextField("@FUTURES");
			unifiedSymbolSymbolTextField.setPrefWidth(180);
			TextField startDateTimePickerTextField = new TextField(startLocalDateTime.format(CommonConstant.DT_FORMAT_WITH_MS_FORMATTER));
			startDateTimePickerTextField.setPrefWidth(160);
			TextField endDateTimePickerTextField = new TextField(endLocalDateTime.format(CommonConstant.DT_FORMAT_WITH_MS_FORMATTER));
			endDateTimePickerTextField.setPrefWidth(160);

			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();

			Button refreshButton = new Button("刷新");

			refreshButton.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					if (me.getButton() == MouseButton.PRIMARY) {

						long startTimestamp = Long.MAX_VALUE;
						try {
							startTimestamp = CommonUtils.localDateTimeToMills(LocalDateTime.parse(startDateTimePickerTextField.getText(), CommonConstant.DT_FORMAT_WITH_MS_FORMATTER));
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("错误");
							alert.setHeaderText("开始时间解析错误");
							alert.setContentText("请使用格式<" + CommonConstant.DT_FORMAT_WITH_MS + ">填充!");

							alert.showAndWait();
							return;
						}

						long endTimestamp = Long.MAX_VALUE;
						try {
							endTimestamp = CommonUtils.localDateTimeToMills(LocalDateTime.parse(endDateTimePickerTextField.getText(), CommonConstant.DT_FORMAT_WITH_MS_FORMATTER));
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("错误");
							alert.setHeaderText("结束时间解析错误");
							alert.setContentText("请使用格式<" + CommonConstant.DT_FORMAT_WITH_MS + ">填充!");

							alert.showAndWait();
							return;
						}

						String unifiedSymbol = unifiedSymbolSymbolTextField.getText();

						if (StringUtils.isBlank(unifiedSymbol)) {
							return;
						}

						if ("TICK".equals(chartType)) {
							chartsDataService.generateTickLineData(startTimestamp, endTimestamp, unifiedSymbol, key);
						} else if ("VOLUME_BAR".equals(chartType)) {
							int volumeBarSize = 0;
							try {
								volumeBarSize = Integer.valueOf(volumeBarSizeTextField.getText());
							} catch (Exception e) {
								logger.error("解析错误", logger);

								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("错误");
								alert.setHeaderText("量输入错误");
								alert.setContentText("请检查量输入值!");

								alert.showAndWait();
								return;
							}

							if (volumeBarSize < 20 || volumeBarSize > 2000000) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("错误");
								alert.setHeaderText("量输入错误");
								alert.setContentText("请检查量输入值在区间[20,2000000]!");

								alert.showAndWait();
								return;
							}
							chartsDataService.generateVolumeBarCandlestickData(startTimestamp, endTimestamp, unifiedSymbol, volumeBarSize, key);
						} else if ("VOL_OPI_CHANGE_DISTRIBUTION".equals(chartType)) {
							chartsDataService.generateVolOPIDeltaHistogramData(startTimestamp, endTimestamp, unifiedSymbol, key);
						} else {

							BarCycleEnum barCycle = BarCycleEnum.valueOf(chartType);
							chartsDataService.generateCandlestickData(startTimestamp, endTimestamp, unifiedSymbol, barCycle, key);
						}

						String port = environment.getProperty("local.server.port");

						webEngine.load("http://127.0.0.1:" + port + "/index.html?key=" + key);
						if ("VOLUME_BAR".equals(chartType)) {
							basicMarketDataChartStage.setTitle(
									unifiedSymbol + " | " + chartType + " | " + volumeBarSize + " | " + startDateTimePickerTextField.getText() + " → " + endDateTimePickerTextField.getText());
						} else {
							basicMarketDataChartStage.setTitle(unifiedSymbol + " | " + chartType + " | " + startDateTimePickerTextField.getText() + " → " + endDateTimePickerTextField.getText());
						}
					}
				}
			});

			menuHBoxLine2.getChildren().addAll(new Text("合约"), unifiedSymbolSymbolTextField, new Text("时间范围"), startDateTimePickerTextField, new Text("-"), endDateTimePickerTextField, refreshButton);
			basicMarketDataChartVBox.getChildren().addAll(menuHBoxLine1, menuHBoxLine2);

			basicMarketDataChartVBox.getChildren().add(browser);
			VBox.setVgrow(browser, Priority.ALWAYS);

			basicMarketDataChartStage.setOnCloseRequest((WindowEvent event1) -> {
				chartsDataService.removeChartData(key);
			});

		}
	}
}
