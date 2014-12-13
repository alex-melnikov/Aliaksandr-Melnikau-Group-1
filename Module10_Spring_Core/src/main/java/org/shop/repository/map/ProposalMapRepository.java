package org.shop.repository.map;

import java.util.List;

import org.apache.commons.collections.Predicate;
import org.shop.data.Proposal;
import org.shop.repository.ProposalRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProposalMapRepository extends AbstractMapRepository<Proposal>
		implements ProposalRepository {

	public Proposal getProposal(Long id) {
		return get(id);
	}

	public Long createProposal(Proposal proposal) {
		return create(proposal);
	}

	public void updateProposal(Proposal proposal) {
		update(proposal);
	}

	public List<Proposal> getProposalsByProductId(Long productId) {
		return select(new ProposalByProductPredicate(productId));
	}

	public List<Proposal> getProposalsBySellerId(Long sellerId) {
		return select(new ProposalBySellerPredicate(sellerId));
	}

	private class ProposalByProductPredicate implements Predicate {

		private Long productId;

		private ProposalByProductPredicate(Long productId) {
			super();
			this.productId = productId;
		}

		public boolean evaluate(Object input) {
			if (input instanceof Proposal) {
				Proposal proposal = (Proposal) input;

				return productId.equals(proposal.getProduct().getId());
			}

			return false;
		}
	}

	private class ProposalBySellerPredicate implements Predicate {
		private Long sellerId;

		private ProposalBySellerPredicate(Long sellerId) {
			super();
			this.sellerId = sellerId;
		}

		public boolean evaluate(Object input) {
			if (input instanceof Proposal) {
				Proposal proposal = (Proposal) input;

				return sellerId.equals(proposal.getSeller().getId());
			}

			return false;
		}
	}
}
