package cs598ga.shull.test.nodes;

import cs598ga.shull.test.execution.ExecutionEnvironment;
import java.util.ArrayList;

public class QueryNode extends ClauseNode implements ExecutableNode {
	public ExecutableNode child;
	//public ArrayList<BaseNode> queries;

	public QueryNode(){
		this.child = SpecialNode.NONE;
	}
	
	/*
	public void addQueries (ArrayList<BaseNode> nodes){
		assert queries == SpecialNode.NONODES : "should only be instantiated once";
		queries = new ArrayList<>();
		queries.addAll(nodes);
	}
	*/

	public void setChild (ExecutableNode node){
		assert child == SpecialNode.NONE : "should only be instantiated once";
		child = node;
	}

	@Override
	public boolean isFact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRule() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isQuery() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ExecutableNode next(ExecutionEnvironment env) {
		env.addLocalEnv(env.createChildLocalEnv());
		return child;
	}

	@Override
	public ExecutableNode backtrack(ExecutionEnvironment env) {
		return SpecialNode.NOBACKTRACK;
	}

}
