package com.wavesplatform.crypto;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TestMerkleTree {

    //TODO false tests

    @Test
    void singleLeaf() {
        MerkleTree tree = MerkleTree.of("one".getBytes());
        byte[] proof = tree.proofByLeafIndex(0);
        assertThat(tree.isProofValid(proof, "one".getBytes())).isEqualTo(true);
    }

    @Test
    void twoLeafs() {
        MerkleTree tree = MerkleTree.of("one".getBytes(), "two".getBytes());
        assertAll(
                () -> { byte[] proof = tree.proofByLeafIndex(0);
                    assertThat(tree.isProofValid(proof, "one".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeafIndex(1);
                    assertThat(tree.isProofValid(proof, "two".getBytes())).isEqualTo(true); }
        );
    }

    @Test
    void nineLeafs() {
        MerkleTree tree = MerkleTree.of(Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
                .map(String::getBytes).collect(toList()));
        assertAll(
                () -> { byte[] proof = tree.proofByLeaf("one".getBytes());
                    assertThat(tree.isProofValid(proof, "one".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("two".getBytes());
                    assertThat(tree.isProofValid(proof, "two".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("three".getBytes());
                    assertThat(tree.isProofValid(proof, "three".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("four".getBytes());
                    assertThat(tree.isProofValid(proof, "four".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("five".getBytes());
                    assertThat(tree.isProofValid(proof, "five".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("six".getBytes());
                    assertThat(tree.isProofValid(proof, "six".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("seven".getBytes());
                    assertThat(tree.isProofValid(proof, "seven".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("eight".getBytes());
                    assertThat(tree.isProofValid(proof, "eight".getBytes())).isEqualTo(true); },
                () -> { byte[] proof = tree.proofByLeaf("nine".getBytes());
                    assertThat(tree.isProofValid(proof, "nine".getBytes())).isEqualTo(true); }
        );
    }

}
