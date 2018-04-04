package cs598ga.shull.test.nodes;

import cs598ga.shull.test.execution.ExecutionEnvironment;
import cs598ga.shull.test.execution.LocalEnvironment;
import cs598ga.shull.test.nodes.executionState.BaseExecutionState;
import cs598ga.shull.test.nodes.executionState.CompoundState;
import cs598ga.shull.test.runtime.PrologRuntime;

import java.util.ArrayList;

public class CompoundNode extends FactNode implements ExecutableNode {
	public AtomNode atom;
	
	private int matchNum = -1;
	private ArrayList<PredicateNode> matches = null;
	
	public CompoundNode(AtomNode base, ArrayList<PredicateNode> children){
		super.base = base.base;
		this.atom = base;
		this.children = children;
	}

	@Override
	public boolean isAtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCompound() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isVariable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return base.getName();
	}

	@Override
	public BaseExecutionState generateExecutionState(){
		return new CompoundState();
	}
	
	/*
	@Override
	public ExecutableNode next(ExecutionEnvironment env) {
		// TODO Auto-generated method stub
		CompoundState state = (CompoundState) env.getCurrentState();
		state.matches = env.globalEnv.getPredicates(base.getName());
		state.matchNum = 0;
		int matchNum = state.matchNum;
		boolean foundMatch = false;
		ExecutableNode result = null;
		for(; matchNum < state.matches.size(); matchNum++){
			PredicateNode node = state.matches.get(matchNum);
			System.out.println("trying to match " + node);
			if(node.canMatch(this, env.getCurrentLocalEnv())){
				node.match(this, env.getCurrentLocalEnv());
				foundMatch = true;
				result = node;
				break;
			}
		}
		state.matchNum = matchNum;
		if(!foundMatch){
			return SpecialNode.DEADEND;
		} else {
			//don't really care about the others
			if(result instanceof RuleNode){
				return result;
			}
		}
		return SpecialNode.FINISHED;
	}
	*/

	@Override
	public ExecutableNode backtrack(ExecutionEnvironment env) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * the FactNode parameter is the source
	 * "this" is the target
	 */
	//FIXME NOTE THAT THIS IS WRONG WHEN CHECKING THE VARIABLES SHOULD ALSO MATCH...
	@Override
	public boolean canMatch(PredicateNode node, LocalEnvironment env) {
		if(node.base.isSourceCurrentlyVariable(env)){
			if(base.isTargetCurrentlyVariable(env)){
				PrologRuntime.programError("didn't think that this can happen...");
				return true;
			} else {
				env.setTargetMatch(node.base.getName(), this);
				return true;
			}
		}
		node = node.base.getSourceCurrentNode(node, env);
		if(base.nameMatches(node.base.getName())){
			//now making sure all children match
			//FIXME this is wrong, 
			if(node.children.size() != children.size()){
				return false;
			}
			for(int i = 0; i < children.size(); i++){
				if(!(children.get(i)).canMatch(node.children.get(i), env)){
					return false;
				}
			}
			//met all requirements
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<String> match(PredicateNode node, LocalEnvironment env) {
		return null;
	}
	
	@Override
	public String toString(){
		String message = "compound node (" + atom + ") [";
		boolean first = true;
		for(PredicateNode child : children){
			if(!first){
				message += ", ";
			}
			message += child;
			first = false;
		}
		message += "]";
		return message;
	}
}