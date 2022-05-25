import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;





public class Algorithm extends JFrame {

	int num[], arraySizes[], arrayCount;
	long compareResultMat[][];
	public JPanel contentPane;
	public JTabbedPane tabbedPane;
	ArrayList<String> list;
	
	public JPanel sortPanel, comparePanel;
	JLabel  selectSortLabel, compareSortLabel, dummyLabel;
	
	ButtonGroup selectSortButtonGrp;
	JRadioButton mergeSortRB, heapSortRB, insertionSortRB, selectionSortRB, BubbleSortRB, quickSortRB, quick3SortRB;
	JCheckBox mergeSortCB, heapSortCB, insertionSortCB, selectionSortCB, BubbleSortCB, quickSortCB, quick3SortCB;
	
	JButton randomNumberBtn, runSortBtn, resetBtn, runCompareBtn;
	JTextArea numberInputTextArea, resultTextArea;
	JTextField toEditText, fromEditText, nEditText;
	JTextField arrayCountET, sizeOfArrayET;
	JLabel runTime;
	long startTime, endTime;
	int cbCount;
	
//	constructor function, it initalizes the frame and it's size, and makes call to other methods to generate the panel
//	and tabbed panes.
	public Algorithm(String title) {
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		add(contentPane);
		cbCount = 0;
		JLabel screenTitle = new JLabel("SORTING ALGORITHMS", JLabel.CENTER);
		tabbedPane = new JTabbedPane();
		contentPane.add(screenTitle, BorderLayout.PAGE_START);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		addPanelSort();
		addPanelCompare();
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		pack();
		setSize(800,550);
		setResizable(false);
		setLocation(300,200);
		
		list = new ArrayList<String>();
		
	}
//	generate the content displayed on compare tab
	public void addPanelCompare() {
		comparePanel = new JPanel();
		comparePanel.setLayout(null);
		comparePanel.setBackground(new Color(235,240,240));
		
		compareSortLabel = new JLabel("Select Algorithm's to compare", JLabel.CENTER);
		compareSortLabel.setBounds(0,0,780,30);
		compareSortLabel.setOpaque(true);
		compareSortLabel.setBackground(new Color(176,195,198));
		compareSortLabel.setBorder(BorderFactory.createEtchedBorder());
		comparePanel.add(compareSortLabel);
		tabbedPane.addTab("Compare Algo",null,comparePanel,null);
		
		JPanel panelCB = new JPanel();
		panelCB.setLayout(new GridLayout(7,1));
		
		mergeSortCB= new JCheckBox("Merge Sort CB");
		heapSortCB= new JCheckBox("Heap Sort");
		insertionSortCB= new JCheckBox("Insertion Sort");
		selectionSortCB= new JCheckBox("Selection Sort"); 
		BubbleSortCB= new JCheckBox("Bubble Sort");
		quickSortCB= new JCheckBox("Quick Sort");
		quick3SortCB= new JCheckBox("Quick 3 Sort");
		
		panelCB.add(mergeSortCB);
		panelCB.add(heapSortCB);
		panelCB.add(insertionSortCB);
		panelCB.add(selectionSortCB);
		panelCB.add(BubbleSortCB);
		panelCB.add(quickSortCB);
		panelCB.add(quick3SortCB);
		
		panelCB.setBounds(0, 40, 150, 350);
		panelCB.setBorder(BorderFactory.createBevelBorder(1));		
		comparePanel.add(panelCB);
		
		runCompareBtn = new JButton("Start Comparison");
		runCompareBtn.setBounds(180,45,120,40);
		runCompareBtn.setMargin(new Insets(0,0,0,0));
		comparePanel.add(runCompareBtn);
		
		JLabel nLabel = new JLabel("Number of Arrays:");
		nLabel.setBounds(325,60,120,25);
		nLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		comparePanel.add(nLabel);
		arrayCountET= new JTextField();
		arrayCountET.setBounds(460,60,200,25);
		comparePanel.add(arrayCountET);
		
		JLabel sLabel = new JLabel("Size of each Array:");
		sLabel.setBounds(325,100,120,25);
		sLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		comparePanel.add(sLabel);
		sizeOfArrayET= new JTextField();
		sizeOfArrayET.setBounds(460,100,200,25);
		comparePanel.add(sizeOfArrayET);
		
		dummyLabel = new JLabel(" button count "+cbCount);
		dummyLabel.setBounds(350,420,400,30);
		comparePanel.add(dummyLabel);	
		
		listenerForCB();
		runCompareListener();
		
		
		
	}
//	listener for checkbox, to hold the count of checkboxes ticketed at any point
	public void listenerForCB() {
		ItemListener itemListener = new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == mergeSortCB || e.getSource() == heapSortCB || e.getSource() == insertionSortCB 
						|| e.getSource() == selectionSortCB || e.getSource() == BubbleSortCB 
						|| e.getSource() == quickSortCB || e.getSource() == quick3SortCB) {
		            if (e.getStateChange() == 1) {
		            	cbCount++;
		            	if(e.getSource() == mergeSortCB)
		            		list.add("MERGE");
		            	else if(e.getSource() == heapSortCB) 
		            		list.add("HEAP");
		            	else if(e.getSource() == insertionSortCB) 
		            		list.add("INSERTION");
		            	else if(e.getSource() == selectionSortCB) 
		            		list.add("SELECTION");
		            	else if(e.getSource() == BubbleSortCB) 
		            		list.add("BUBBLE");
		            	else if(e.getSource() == quickSortCB) 
		            		list.add("QUICK");
		            	else if(e.getSource() == quick3SortCB) 
		            		list.add("QUICK3");
		            }
		                
		            else {
		            	cbCount--;
		            	if(e.getSource() == mergeSortCB)
		            		list.remove("MERGE");
		            	else if(e.getSource() == heapSortCB) 
		            		list.remove("HEAP");
		            	else if(e.getSource() == insertionSortCB) 
		            		list.remove("INSERTION");
		            	else if(e.getSource() == selectionSortCB) 
		            		list.remove("SELECTION");
		            	else if(e.getSource() == BubbleSortCB) 
		            		list.remove("BUBBLE");
		            	else if(e.getSource() == quickSortCB) 
		            		list.remove("QUICK");
		            	else if(e.getSource() == quick3SortCB) 
		            		list.remove("QUICK3");
		            }
		                
		        }
			}
		};
		mergeSortCB.addItemListener(itemListener);
		heapSortCB.addItemListener(itemListener);
		insertionSortCB.addItemListener(itemListener);
		selectionSortCB.addItemListener(itemListener);
		BubbleSortCB.addItemListener(itemListener);
		quickSortCB.addItemListener(itemListener);
		quick3SortCB.addItemListener(itemListener);
		
	}
// display the graph using the matrix that holds the runtime values for selected algo.
	public void displayGraph() {
//	Reference:	https://www.codejava.net/java-se/graphics/using-jfreechart-to-draw-line-chart-with-categorydataset
//		// https://www.javatpoint.com/jfreechart-numberaxis-class
//		https://www.youtube.com/watch?v=skxH0oX6XlI&list=PLS1QulWo1RIbx1dvFMTFOMxzAmO28Qoc9&index=5
		DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
		for(int k=0;k<cbCount;k++) {
			for(int i =0; i<arrayCount;i++) {
				defaultCategoryDataset.setValue(compareResultMat[i][k] ,""+list.get(k),""+arraySizes[i]);
				}
			}
		JFreeChart jFreeChart = ChartFactory.createLineChart("Performance Stats.","Array Size","RunTime in MicroSeconds",defaultCategoryDataset,PlotOrientation.VERTICAL,true, true, false);
		
		CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
		categoryPlot.setRangeGridlinePaint(Color.black);
		
		
		
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		categoryPlot.setRenderer(renderer);
		categoryPlot.setOutlinePaint(Color.BLUE);
		categoryPlot.setOutlineStroke(new BasicStroke(2.0f));
		categoryPlot.setBackgroundPaint(Color.WHITE);
		categoryPlot.setRangeGridlinesVisible(true);
		categoryPlot.setRangeGridlinePaint(Color.BLACK);
		categoryPlot.setDomainGridlinesVisible(true);
		categoryPlot.setDomainGridlinePaint(Color.BLACK);
		
		
		ChartFrame  frame = new ChartFrame("bar chart", jFreeChart);
		frame.setVisible(true);
		frame.setSize(800,800);
		
		dummyLabel.setText(" count value: "+cbCount);
	}
// listener for button that starts the comparison logic
	public void runCompareListener() {
		ActionListener runSortListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!arrayCountET.getText().equals("") && !sizeOfArrayET.getText().equals("") && cbCount >1) {
//					dummyLabel.setText(" count : "+cbCount);
					String sizeString[] = sizeOfArrayET.getText().toString().split(",");
					arraySizes = new int[sizeString.length];
					if(isNumber(arrayCountET.getText()) && isNumberArray(sizeString, arraySizes)){
						arrayCount = Integer.parseInt(arrayCountET.getText());
						if(arrayCount != arraySizes.length)
							dummyLabel.setText(" Array count and different array sizes do not match");
						else {
							System.out.println(" number of array : "+ arrayCount);
							System.out.println(" size array length : "+ arraySizes.length);
							
							compareResultMat = new long[arrayCount][cbCount];
							
							for(int j = 0;j < arrayCount; j++) {
//								generate random number array for first input size
								int number[] = new int[arraySizes[j]];
								Random random = new Random();
								for(int i =0; i<number.length;) {
									number[i++] = random.nextInt(500);
								}
																
								for(int i=0;i<list.size();i++) {
									int numberOP[]  = Arrays.copyOf(number, number.length);
									String sortName = list.get(i);
									if(sortName.equals("MERGE")) {
										int result[] = new int[numberOP.length];
										startTime = System.nanoTime();
										result = mergeSort(0, numberOP.length-1, numberOP);
										endTime = System.nanoTime();
										
									}
									else if(sortName.equals("HEAP")){
										heapSort(numberOP);
									}
									else if(sortName.equals("INSERTION")){
										insertionSort(numberOP);
									}
									else if(sortName.equals("SELECTION")){
										selectionSort(numberOP);
									}
									else if(sortName.equals("BUBBLE")){
										bubbleSort(numberOP);
									}
									else if(sortName.equals("QUICK")){
										startTime = System.nanoTime(); 
										quickSort(numberOP, 0, numberOP.length-1);
										endTime = System.nanoTime();
									}
									else if(sortName.equals("QUICK3")){
										startTime = System.nanoTime();
										quick3Sort( 0, numberOP.length-1, numberOP);
										endTime = System.nanoTime();
									}
									compareResultMat[j][i] = (endTime-startTime)/1000;
								}
							}
							
							for(int i=0;i<list.size();i++) {
								System.out.print("     "+list.get(i));
							}
							System.out.println();
							for(int i =0; i<arrayCount;i++) {
								System.out.print("size("+arraySizes[i]+")   ->");
								for(int k=0;k<cbCount;k++) {
									System.out.print(compareResultMat[i][k]+"   ");
								}
								System.out.println();
							}
						}
					}
					else {
						dummyLabel.setText("numeric array count and size required");
					}

					displayGraph();
				}
				else {
					dummyLabel.setText("give all required inputs");
				}
			}
		};
		
		runCompareBtn.addActionListener(runSortListener);
	}
//	generate the content displayed on select algo tab
	public void addPanelSort() {
		
		sortPanel = new JPanel();
		sortPanel.setLayout(null);
		sortPanel.setBackground(new Color(235,240,240));
		
		selectSortLabel = new JLabel("Select Sorting Algorithm",JLabel.CENTER);
		selectSortLabel.setBounds(0,0,780,30);
		selectSortLabel.setOpaque(true);
		selectSortLabel.setBackground(new Color(176,195,198));
		selectSortLabel.setBorder(BorderFactory.createEtchedBorder());
		sortPanel.add(selectSortLabel);
		displaySelectAlgoRB();
		tabbedPane.addTab("Sorting Algo.",null,sortPanel,null);
		
		randomGenerateListener();
		runSortListener();
		resetInputListener();
	}
//	generate the content displayed on select algo tab like radio button, input fields etc.
	public void displaySelectAlgoRB() {
		
		JPanel panelRBGroup = new JPanel();
		panelRBGroup.setLayout(new GridLayout(7,1));
		panelRBGroup.setBounds(0, 40, 150, 350);
		selectSortButtonGrp =  new ButtonGroup();
		mergeSortRB= new JRadioButton("Merge Sort");
		heapSortRB= new JRadioButton("Heap Sort");
		insertionSortRB= new JRadioButton("Insertion Sort");
		selectionSortRB= new JRadioButton("Selection Sort"); 
		BubbleSortRB= new JRadioButton("Bubble Sort");
		quickSortRB= new JRadioButton("Quick Sort");
		quick3SortRB= new JRadioButton("Quick 3M Sort");
		
		selectSortButtonGrp.add(mergeSortRB);
		selectSortButtonGrp.add(heapSortRB);
		selectSortButtonGrp.add(insertionSortRB);
		selectSortButtonGrp.add(selectionSortRB);
		selectSortButtonGrp.add(BubbleSortRB);
		selectSortButtonGrp.add(quickSortRB);
		selectSortButtonGrp.add(quick3SortRB);
		
		panelRBGroup.add(mergeSortRB);
		panelRBGroup.add(heapSortRB);
		panelRBGroup.add(insertionSortRB);
		panelRBGroup.add(selectionSortRB);
		panelRBGroup.add(BubbleSortRB);
		panelRBGroup.add(quickSortRB);
		panelRBGroup.add(quick3SortRB);
		
		panelRBGroup.setBorder(BorderFactory.createBevelBorder(1));		
		
		sortPanel.add(panelRBGroup);
		
		JLabel randomRangeLabel = new JLabel("Please provide range for random number generation");
		randomRangeLabel.setBounds(350,35,300,30);
		sortPanel.add(randomRangeLabel);
		
		randomNumberBtn = new JButton("Random Number");
		randomNumberBtn.setBounds(180,45,120,40);
		randomNumberBtn.setMargin(new Insets(0,0,0,0));
		sortPanel.add(randomNumberBtn);
		
		JLabel nLabel = new JLabel("N* :");
		nLabel.setBounds(325,60,40,25);
		nLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sortPanel.add(nLabel);
		
		nEditText= new JTextField();
		nEditText.setBounds(370,60,80,25);
		sortPanel.add(nEditText);
		
		JLabel rangeFromLabel = new JLabel("From* :");
		rangeFromLabel.setBounds(480,60,40,25);
		sortPanel.add(rangeFromLabel);
		
		fromEditText = new JTextField();
		fromEditText.setBounds(525,60,80,25);
		sortPanel.add(fromEditText);
		
		JLabel rangeToLabel = new JLabel("To* :");
		rangeToLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		rangeToLabel.setBounds(625,60,40,25);
		sortPanel.add(rangeToLabel);
		
		
		toEditText = new JTextField();
		toEditText.setBounds(670,60,80,25);
		sortPanel.add(toEditText);
		
		JLabel inputLabel = new JLabel("Input* :");
		inputLabel.setBounds(350,80,80,25);
		sortPanel.add(inputLabel);
		
		numberInputTextArea = new JTextArea();
		numberInputTextArea.setBorder(BorderFactory.createBevelBorder(1));
		numberInputTextArea.setLineWrap(true);
		JScrollPane scrollInput = new JScrollPane(numberInputTextArea);
		scrollInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollInput.setBounds(350,110,400,150);
		sortPanel.add(scrollInput);
		
		JLabel outputLabel = new JLabel("Output :");
		outputLabel.setBounds(350,260,80,25);
		sortPanel.add(outputLabel);
		
		resultTextArea = new JTextArea();
		resultTextArea.setBorder(BorderFactory.createBevelBorder(1));
		resultTextArea.setEditable(false);
		resultTextArea.setLineWrap(true);
		resultTextArea.setBackground(Color.LIGHT_GRAY);
		JScrollPane scrollResult = new JScrollPane(resultTextArea);
		scrollResult.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollResult.setBounds(350,290,400,150);
		sortPanel.add(scrollResult);
		
		runSortBtn = new JButton("Run Sort");
		runSortBtn.setBounds(180,100,120,40);
		sortPanel.add(runSortBtn);
		
		resetBtn = new JButton("Reset Input");
		resetBtn.setBounds(180,155,120,40);
		sortPanel.add(resetBtn);
		
		runTime = new JLabel("Runtime in Micro seconds: ");
		runTime.setBounds(350,440,200,30);
		sortPanel.add(runTime);	
	}
//	listener for random generator button, to generate random numbers upon button click.
	public void randomGenerateListener() {
		ActionListener randomGenerateListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(isNumber(toEditText.getText()) && isNumber(fromEditText.getText()) && isNumber(nEditText.getText())) {
					int from = Integer.parseInt(fromEditText.getText());
					int to = Integer.parseInt(toEditText.getText());
					int n =  Integer.parseInt(nEditText.getText());
					num =new int[n];
					if(!(from < to))
						resultTextArea.setText("invalid range");
					else {
						Random random = new Random();
						for(int i =0; i<n;) {
							int temp = random.nextInt(to);
							if(temp>=from && temp<=to)
								num[i++] = temp;
						}
						StringBuilder stringBuilder = new StringBuilder();
						for(int i = 0;i<n; i++) {
							stringBuilder.append(num[i]+",");
						}
						numberInputTextArea.setText(stringBuilder.toString());
					}
				}
				else {
					resultTextArea.setText("not numeric");
				}
			}
		};
		randomNumberBtn.addActionListener(randomGenerateListener);
	}
//  listener for run sort algorithm button 
	public void runSortListener() {
		ActionListener runSortListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String numString[] = numberInputTextArea.getText().toString().split(",");
//				resultTextArea.setText(numString.length+" ");
				boolean sortingPerformed = false;
				num = new int[numString.length];
				if(isNumberArray(numString,num)) {
					if(mergeSortRB.isSelected()) {
						int result[]=  Arrays.copyOf(num, num.length);;
						startTime = System.nanoTime();
						num = mergeSort(0, result.length-1, result);
						endTime = System.nanoTime();
						sortingPerformed = true;
					}
					else if(heapSortRB.isSelected()) {
						heapSort(num);
						sortingPerformed = true;
					}
					else if(insertionSortRB.isSelected()) {
						insertionSort(num);
						sortingPerformed = true;
					}
					else if(selectionSortRB.isSelected()) {
						selectionSort(num);
						sortingPerformed = true;
					}
					else if(BubbleSortRB.isSelected()) {
						bubbleSort(num);
						sortingPerformed = true;
					}
					else if(quickSortRB.isSelected()) {
						startTime = System.nanoTime(); 
						quickSort(num, 0, num.length-1);
						endTime = System.nanoTime();
						sortingPerformed = true;
					}
					else if(quick3SortRB.isSelected()) {
						startTime = System.nanoTime();
						quick3Sort( 0, num.length-1, num);
						endTime = System.nanoTime();
						sortingPerformed = true;
					}
					else {
						sortingPerformed = false;
						resultTextArea.setText("Please select one algorithm to proceed");
					}
					if(sortingPerformed ) {
						StringBuilder stringBuilder= new StringBuilder();
						for(int i=0;i<num.length;i++) {
							stringBuilder.append(num[i]+",");
						}
						resultTextArea.setText(stringBuilder.toString());
						runTime.setText("Runtime in MicroSeconds: "+(endTime-startTime)/1000);
					}
				}else {
					resultTextArea.setText("Invalid Input!! Please check");
				}
			}
		};
		runSortBtn.addActionListener(runSortListener);
	}
//	listener for reset input button
	public void resetInputListener() {
		ActionListener actionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resultTextArea.setText("");
				numberInputTextArea.setText("");;
				toEditText.setText("");
				fromEditText.setText("");
				nEditText.setText("");
			}
		};
		resetBtn.addActionListener(actionListener);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Algorithm algorithm = new Algorithm("DAA Project");
				algorithm.setVisible(true);
			}
		};
		
		try {
			EventQueue.invokeAndWait(runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
//  helper method to check if all the input is number
	public boolean isNumber(String s) {
		try {
			int num = Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;	
	}
//  helper method to check if all the elements of given array are integer
	public boolean isNumberArray(String[] string, int[] num ) {
//		num = new int[string.length];
		try {
			for(int i = 0; i<string.length;i++) {
				num[i] = Integer.parseInt(string[i]);
			}
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}
//  insertion sort logic
	public void insertionSort(int[] num) {
		startTime = System.nanoTime(); 
		
		for(int i=1;i<num.length;i++) {
			int j = i-1;
			int keyEle = num[i];
			while(j>=0 && num[j]>keyEle) {
				num[j + 1] = num[j];
                j = j - 1;
			}
			num[j+1] = keyEle; 
		}
		
		endTime = System.nanoTime();
//		runTime.setText("Runtime in Milliseconds: "+(endTime-startTime));
		
	}
//  selection sort logic
	public void selectionSort(int[] num) {
		startTime = System.nanoTime();
		for(int i=0;i<num.length-1;i++) {
			int min = num[i];
			int minIndex = i;
			for(int j=i+1; j < num.length; j++) {
				if(num[j]<min) {
					min = num[j];
					minIndex = j;
				}
			}
			num[minIndex] = num[i];
			num[i] = min;
			
		}
		endTime = System.nanoTime();
//		runTime.setText("Runtime in Milliseconds: "+(endTime-startTime));
		
	}
//  Merge sort logic
	public int[] mergeSort(int start, int end, int[] num) {
		
		if (start == end) return new int[]{num[start]};
		
		if(start<end) {
			int[] leftArray = mergeSort(start, start + (end-start)/2, num);
			int[] rightArray = mergeSort((start + (end-start)/2)+1, end, num);
			
			return mergeArray(leftArray, rightArray);	
		}
		
		return new int[0];
	}
//	Merge two arrays
	public int[] mergeArray(int[] leftArray, int[] rightArray) {
		
		int i = 0, j = 0;
        int k = 0;
        int[] result = new int[leftArray.length + rightArray.length];
        
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                result[k++] = leftArray[i++];
            }
            else {
            	result[k++] = rightArray[j++];
            }
        }
  
        while (i <  leftArray.length) {
        	result[k++] = leftArray[i++];
        }
  
        while (j < rightArray.length) {
        	result[k++] = rightArray[j++];
        }	
        
        return result;
        
	}
//	Bubble sort logic
	public void bubbleSort(int[] num) {
		startTime = System.nanoTime(); 
		int temp ;
        for (int i = 0; i < num.length - 1; i++) {
        	for (int j = 0; j < num.length-i-1; j++) {
        		if (num[j] > num[j + 1]) {
                    temp = num[j];
                    num[j] = num[j+1];
                    num[j+1] = temp;
                }
        	}
                
        }
            
        endTime = System.nanoTime();
//		runTime.setText("Runtime in Milliseconds: "+(endTime-startTime));
	}
//	Heap sort logic
	public void heapSort(int[] num) {
		startTime = System.nanoTime();
		 int n = num.length;
		 int temp;
	        for (int i = n/2-1;i>=0; i--)
	            heapifyList(num, n, i);
	        for (int i = n - 1; i > 0; i--) {
	            temp = num[0];
	            num[0] = num[i];
	            num[i] = temp;
	            heapifyList(num, i, 0);
	        }
	        endTime = System.nanoTime();			
	}
//	heapify method used to create heap tree and heapify everytime the index 0 element is removed
	public void heapifyList(int arr[], int n, int i)
    {
       
        int leftChild = 2 * i + 1; 
        int rightChild = 2 * i + 2; 
        int largest=i; 
        
        if (leftChild < n && arr[leftChild] > arr[largest])
            largest=leftChild;
 
        if (rightChild < n && arr[rightChild] > arr[largest])
            largest=rightChild;
 
        if (largest != i) {
            int temp=arr[i];
            arr[i]=arr[largest];
            arr[largest]=temp;
            heapifyList(arr, n, largest);
        }
    }
//	quick sort
	public void quickSort(int num[], int start, int end) {
		if (start < end)
	    {
	        int pivot = partition( start, end, num);
	        quickSort(num, start, pivot - 1);
	        quickSort(num, pivot + 1, end);
	    }
	}
//	return the partitioned array with smaller elements on left and larger elements on right of pivot
	public int partition( int start, int end, int[] arr)
	{
	    int pivot = arr[end];
	     
	    int i = (start - 1);
	 
	    for(int j = start; j <= end - 1; j++)
	    {
	        if (arr[j] < pivot)
	        {
	            i++;
	            swapNumber(arr, i, j);
	        }
	    }
	    swapNumber(arr, i + 1, end);
	    return (i + 1);
	}
//	helper method for swapping number
	public void swapNumber(int[] arr, int i, int j)
	{
	    int temp = arr[i];
	    arr[i] = arr[j];
	    arr[j] = temp;
	}
	
	public void quick3Sort(int start, int end, int[] array) {
		int size = end - start + 1;
		if (size <= 3)
			performManualSort(start, end, array);
		else {
			double medianPos = median(start, end, array);
			int partition = partitionFor3Median( start, end, medianPos, array);
			quick3Sort( start, partition - 1, array);
			quick3Sort( partition + 1, end, array);
		}
	}
//	perform manual sort incase the array size is 3, used for quick sort with 3 median logic
	public  void performManualSort(int start, int end, int[] array) {
		int size = end - start + 1;
		if (size <= 1)
			return;
		if (size == 2) {
			if (array[start] > array[end])
				swapNumber(array, start, end);
			return;
		} else {
			if (array[start] > array[end - 1])
				swapNumber(array, start, end - 1);
			if (array[start] > array[end])
				swapNumber(array, start, end);
			if (array[end - 1] > array[end])
				swapNumber(array, end - 1, end);
		}
	}
	
	public int partitionFor3Median( int start, int end, double pivot, int[] array) {
		int lp = start;
		int rp = end - 1;

		while (true) {
			while (array[++lp] < pivot);
			while (array[--rp] > pivot);
			
			if (lp >= rp) {
				break;
			}
			else {
				swapNumber(array, lp, rp);
			}
		}
		swapNumber(array, lp, end - 1);
		return lp;
	}
//	returns the median for quick sort with 3 median logic
	public  int median(int start, int end, int[] array) {
		int mid = (start + end) / 2;

		if (array[start] > array[mid])
			swapNumber(array, start, mid);

		if (array[start] > array[end])
			swapNumber(array, start, end);

		if (array[mid] > array[end])
			swapNumber(array, mid, end);

		swapNumber(array, mid, end - 1);
		return array[end - 1];
	}
}



