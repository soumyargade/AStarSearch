import java.io.*;
import java.util.*;

public class Assign3Search {

	public static double finalCost = 0.0;
	//<input file> <startname> <endname>
	public static void main(String[] args) throws IOException {
		BufferedReader scanner = new BufferedReader(new FileReader(args[0]));
		Assign3Search programm = new Assign3Search();
		ArrayList<Node> people = new ArrayList<Node>();
		people = programm.start(scanner);
		int index = people.size() - 1;
		people.remove(index);
		for (Node person : people) {
			//System.out.println(person.toString());
			//double latitude = Double.parseDouble(person.latitude);
			//double longitude = Double.parseDouble(person.longitude);
			//System.out.println(latitude+longitude);
		}
		//System.out.println();
		people = programm.middle(scanner, people);
		for (Node person : people) {
			//System.out.println("The person is: " + person.name);
			try {
				for (Edge e: person.adjacencies) {
					//System.out.println("The target: " + e.target.name);
					//System.out.println("Cost to get there: " + e.cost);
				}
			}
			catch (NullPointerException e) {
				//System.out.println("None");
			}
			//System.out.println();
		}
		String startName = args[1];
		String endName = args[2];
		//System.out.println(startName);
		//System.out.println(endName);
		Node start = null;
		Node end = null;
		for (Node person : people) {
			if (person.name.equals(startName)) {
				start = person;
			}
			if (person.name.equals(endName)) {
				end = person;
			}
		}
		//System.out.println(start.toString());
		//System.out.println(end.toString());

		aStarSearch(start, end);
		List<Node> path = printPath(end);
		System.out.println("Path: " + path);
		System.out.println("Path Cost: " + Math.round(finalCost * 1000.0) / 1000.0);
	}

	public static List<Node> printPath(Node target) {
		List<Node> path = new ArrayList<Node>();
		double cost = 0.0;
		for (Node node = target; node != null; node = node.parent) {
			//...
			path.add(node);
			cost += node.f_score;
		}
		Collections.reverse(path);
		finalCost = cost;
		return path;
	}

	public ArrayList<Node> start(BufferedReader scanner) throws IOException {
		
		String line = scanner.readLine(); //skip first line
		ArrayList<Node> people = new ArrayList<Node>();
		while (!line.isEmpty()) {
			line = scanner.readLine();
			String[] tokens = line.split("\\s+");
			String tokenStr = Arrays.toString(tokens);
			tokenStr = tokenStr.replaceAll(",", "");
			tokenStr = tokenStr.replace("[", "");
			tokenStr = tokenStr.replace("]", "");
			//System.out.println(tokenStr);
			Object[] dummy = new Object[3];
			int i = 0;
			for (String s : tokenStr.split(" ")) {
				dummy[i] = s;
				//System.out.print(dummy[i] + " ");
				i += 1;
			}
			people.add(new Node((String)dummy[0], (String)dummy[1], (String)dummy[2]));
			//System.out.println();
		}
		return people;
	}

	//Task: Assign adjacencies to the nodes
	public ArrayList<Node> middle(BufferedReader scanner, ArrayList<Node> people) throws IOException {
		String line = scanner.readLine();
		while ((line = scanner.readLine()) != null) {
			//System.out.println(line);
			String[] tokens = line.split("\\s+");
			String tokenStr = Arrays.toString(tokens);
			tokenStr = tokenStr.replaceAll(",", "");
			tokenStr = tokenStr.replace("[", "");
			tokenStr = tokenStr.replace("]", "");
			Object[] dummy = new Object[3];
			int i = 0;
			for (String s : tokenStr.split(" ")) {
				dummy[i] = s;
				//System.out.print(s + " ");
				i += 1;
			}
			//System.out.println();
			for (Node person : people) {
				Node target = null;
				if (person.name.equals(dummy[1])) {
					//System.out.println("Main Node is " + person.name);
					//System.out.println("Target Node is " + dummy[0]);
					//System.out.println("Cost value is " + dummy[2]);
					for (Node targetPerson : people) {
						if (targetPerson.name.equals(dummy[0])) {
							target = targetPerson;
							double cost = Double.parseDouble((String)dummy[2]);
							person.adjacencies.add(new Edge(target, cost));
						}
					}
				}

				if (person.name.equals(dummy[0])) {
					for (Node targetPerson : people) {
						if (targetPerson.name.equals(dummy[1])) {
							target = targetPerson;
							double cost = Double.parseDouble((String)dummy[2]);
							person.adjacencies.add(new Edge(target, cost));
						}
					}
				}
			}
			//System.out.println();
		}
		return people;
	}

	public static void aStarSearch(Node start, Node end) {
		//System.out.println("Start: " + start.name);
		//System.out.print("Children: ");

		Set<Node> explored = new HashSet<Node>();
		PriorityQueue<Node> queue = new PriorityQueue<Node>(20, new Comparator<Node>() {
			public int compare(Node i, Node j) {
				if (i.f_score > j.f_score) {
					return 1;
				}
				else if (i.f_score < j.f_score) {
					return -1;
				}
				else {
					return 0;
				}
			}
		});

		//Cost from start
		start.g_score = 0;
		queue.add(start);
		boolean found = false;

		while((!queue.isEmpty())&&(!found)) {
			System.out.println("Current Queue: ");
			for (Node n : queue) {
				System.out.print(n.name + " ");
				System.out.print(Math.round(n.f_score * 1000.0) / 1000.0 + " ");
			}
			System.out.println();

			//...
			Node current = queue.poll();
			System.out.println("Node being expanded: " + current.name);
			System.out.println();
			explored.add(current);

			if (current.name.equals(end.name)) {
				found = true;
			}

			for (Edge e : current.adjacencies) {
				//...
				Node child = e.target;
				double cost = e.cost;
				double temp_g_score = current.g_score + cost;

				double x1 = Double.parseDouble(end.latitude);
				double y1 = Double.parseDouble(end.longitude);
				double x2 = Double.parseDouble(child.latitude);
				double y2 = Double.parseDouble(child.longitude);
				double fullDistance = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
				double h_score = Math.round(fullDistance * 1000.0) / 1000.0;

				double temp_f_score = temp_g_score + h_score;
				if ((explored.contains(child)) && (temp_f_score >= child.f_score)) {
					//...
					continue;
				}
				else if ((!queue.contains(child)) || (temp_f_score < child.f_score)) {
					//...
					child.parent = current;
					child.g_score = temp_g_score;
					child.f_score = temp_f_score;
					if (queue.contains(child)) {
						queue.remove(child);
					}
					queue.add(child);
				}
			}
		}
	}
}