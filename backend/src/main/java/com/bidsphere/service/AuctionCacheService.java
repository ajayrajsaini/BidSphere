package com.bidsphere.service;

import com.bidsphere.model.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuctionCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String ALL_AUCTIONS_KEY = "auctions::all";
    private static final String AUCTION_KEY_PREFIX = "auctions::";

    // Cache all auctions
    public void cacheAllAuctions(List<Auction> auctions) {
        redisTemplate.opsForValue().set(ALL_AUCTIONS_KEY, auctions, 1, TimeUnit.HOURS);
        auctions.forEach(this::cacheAuction); // Also cache individual ones
    }
//
    public List<Auction> getAllAuctionsFromCache() {
        Object data = redisTemplate.opsForValue().get(ALL_AUCTIONS_KEY);
        return (data instanceof List) ? (List<Auction>) data : null;
    }

    public void cacheAuction(Auction auction) {
        String key = AUCTION_KEY_PREFIX + auction.getId();
        redisTemplate.opsForValue().set(key, auction, 60, TimeUnit.MINUTES);
    }

    public Auction getAuctionFromCache(String id) {
        String key = AUCTION_KEY_PREFIX + id;
        Object cached = redisTemplate.opsForValue().get(key);
        return cached instanceof Auction ? (Auction) cached : null;
    }

    public void removeAuctionCache(String id) {
        redisTemplate.delete(AUCTION_KEY_PREFIX + id);
    }
    public void removeAllAuctions(){
        redisTemplate.delete(ALL_AUCTIONS_KEY);
    }
}