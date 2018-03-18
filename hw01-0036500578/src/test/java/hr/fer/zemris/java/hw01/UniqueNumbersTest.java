package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.addNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.treeSize;
import static hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class UniqueNumbersTest {

	@Test
	public void addFirstNode() {
		TreeNode head = null;
		head = addNode(head, 5);
		Assert.assertEquals(5, head.value);
	}

	@Test
	public void addAditionalNodes() {
		TreeNode head = null;

		head = addNode(head, 5);
		head = addNode(head, 1);
		head = addNode(head, 7);
		head = addNode(head, 4);
		head = addNode(head, 6);

		Assert.assertEquals(5, head.value);
		Assert.assertEquals(1, head.left.value);
		Assert.assertEquals(7, head.right.value);
		Assert.assertEquals(4, head.left.right.value);
		Assert.assertEquals(6, head.right.left.value);

	}

	@Test
	public void numberOfNodes() {
		TreeNode head = null;
		
		head = addNode(head,5);
		head = addNode(head,1);
		head = addNode(head,7);
		head = addNode(head,5);
		head = addNode(head,6);
		head = addNode(head,1);
		
		Assert.assertEquals(4,treeSize(head));
	}

}
