--
-- Created by IntelliJ IDEA.
-- User: lirui
-- Date: 2020/1/28
-- Time: 9:06 下午
-- To change this template use File | Settings | File Templates.
--
-- Chinese Characters are forbbiden in the following script

-- get red package list
local listKey = 'red_envelope_list_' .. KEYS[1]
-- get red package key
local redPacket = 'red_envelope_' .. KEYS[1]
-- get stock
-- HMSET red_packet_1 stock 20000 unit_amount 10 or set by API code when a user try to send red package
local stock = tonumber(redis.call('hget', redPacket, 'stock'))
-- 1. stock is empty ==> return 0
if stock <= 0 then
    return 0
end

-- 2. else stock = stock-1
stock = stock - 1

-- 3. reset stock
redis.call('hset', redPacket, 'stock', tostring(stock))
-- 4. push red package info into redis list
redis.call('rpush', listKey, ARGV[1])
-- stock == 0 and return 2 means that activity is ended and redis list need to save into mysql
if stock == 0 then
    return 2
end
-- if it is not the last one then return 1 indicating grab red package successfully
return 1

