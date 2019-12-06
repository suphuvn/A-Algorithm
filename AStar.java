import java.util.Stack;

public class AStar {

	int numMissionaries, seats;

	Action action;
	Stack<State> destination;
	Stack<String> history;

	public AStar(int numMissionaries, int seats) {
		System.out.println("A* numMissionaries=" + numMissionaries + " seats=" + seats);

		this.numMissionaries = numMissionaries;
		this.seats = seats;

		action = new Action(numMissionaries, seats);
		history = new Stack<>();
		State state = new State(numMissionaries, seats);
		destination = new Stack<>();
		destination.push(state);
		int limit = state.getHeuristic();
		int result;
		
		while (true) {
			System.out.println("depth = " + limit);
			result = search(0, limit);
			if (result == 0 || result == -1) {
				break;
			}
			limit = result;
		}
		if (result == 0) {
			State child = destination.pop();
			while(!destination.empty()) {
				history.push(destination.peek().toDestination(child));
				child = destination.pop();
			}
			int x = 1;
			while (!history.empty()) {
				System.out.println("Step "+ x+ ": "+ history.pop());
				x++;
			}
		} else {
			System.out.println("No solution was found");
		}
	}
	
	int search(int j, int limit) {
		State state = destination.peek();
		int k = j + state.getHeuristic();

		if (k > limit) {
			return k;
		} else if (state.isFinish()) {
			System.out.println("Optimal solution was found");
			return 0;
		}

		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < action.missionariesBoat.size(); i++) {
			State child = state.performAction(action.missionariesBoat.get(i), action.cannibalsBoat.get(i));
			
			if (child != null && !destination.contains(child)) {
				destination.push(child);
				int result = search(j + 1, limit);
				if (result > 100) {
					System.out.println(child);
				}
				if (result == 0) {
					return 0;
				}
				if (result != -1 && result < min) {
					min = result;
				}
				destination.pop();
			}
		}
		return min == Integer.MAX_VALUE ? - 1 : min;
	}

}
