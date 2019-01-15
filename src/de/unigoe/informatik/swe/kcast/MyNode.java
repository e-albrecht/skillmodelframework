package de.unigoe.informatik.swe.kcast;

import java.util.Set;

import de.unigoe.informatik.swe.krm.KnowledgeComponent;

/**
 * Represents a node in the KC-AST
 * @author Ella Albrecht
 *
 */
public abstract class MyNode {
	
	/**
	 * The piece of code that is represented by the node
	 */
	private String rawSignature;
	
	/**
	 * The position within the source code
	 */
	private int position;
	
	/**
	 * The length of this piece of code
	 */
	private int length;
	
	/**
	 * 
	 * @return The piece of code that is represented by the node
	 */
	public String getRawSignature() {
		return rawSignature;
	}
	
	/**
	 * 
	 * @param rawSignature The piece of code that is represented by the node
	 */
	public void setRawSignature(String rawSignature) {
		this.rawSignature = rawSignature;
	}
	
	/**
	 * 
	 * @return The position within the source code
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * 
	 * @param position The position within the source code
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * 
	 * @return The length of this piece of code
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * 
	 * @param length The length of this piece of code
	 */ 
	public void setLength(int length) {
		this.length = length;
	}
	
	@Override
	public String toString() {
		return rawSignature;
	}
	
	/**
	 * Returns the KCs on a given level used in the node
	 * @param level The level of KCs
	 * @return Set of KCs used in the initialization on the given KC level
	 */
	public abstract Set<KnowledgeComponent> getKCs(int level);
	


}
