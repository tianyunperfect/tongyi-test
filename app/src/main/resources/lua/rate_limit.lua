local key = KEYS[1]
local limit = tonumber(ARGV[1])
local current_time = tonumber(ARGV[2])
local expire_time = tonumber(ARGV[3])

local listLength = redis.call('llen', key)
if listLength < limit then
    redis.call('lpush', key, current_time)
    redis.call('expire', key, expire_time * 2)
else
    local time = redis.call('lindex', key, -1)
    if current_time - time < expire_time * 1000 then
        return "访问频率超过了限制，请稍后再试"
    else
        redis.call('lpush', key, current_time)
        redis.call('ltrim', key, 0, limit - 1)
        redis.call('expire', key, expire_time * 2)
    end
end
return "OK"
