package ec.com.levelap.gameclub.module.kushki.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@Repository
public interface KushkiSubscriptionRepo extends JpaRepository<KushkiSubscription, Long> {

	public KushkiSubscription findByPublicUser(PublicUser publicUser);

	public Page<KushkiSubscription> findAll(Pageable pageable);
	
	public KushkiSubscription findBySubscriptionId(String subscriptionId);
	
	public KushkiSubscription findByPublicUserId(Long id);

}
