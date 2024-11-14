package com.lontten.util.crypto;

import com.google.common.base.Preconditions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PasswordUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 结果
     */
    public static boolean matchesPassword(@Nullable String rawPassword, @Nullable String encodedPassword) {
        if (!StringUtils.hasText(rawPassword)) {
            return false;
        }
        if (!StringUtils.hasText(encodedPassword)) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    @Nonnull
    public static String encodedPassword(@Nullable String rawPassword) {
        Preconditions.checkNotNull(rawPassword, "密码不能为空");
        return passwordEncoder.encode(rawPassword);
    }

}
