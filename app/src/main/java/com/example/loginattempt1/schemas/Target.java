package com.example.loginattempt1.schemas;

import java.math.BigDecimal;

public class Target extends Schema{

    private BigDecimal Reward;
    private boolean IsParent;
    private String target;

    // Retrieve attributes from builder
    private Target(Builder B){
        Reward = B.Reward;
        IsParent = B.IsParent;
        target = B.target;
        setCollection("Target");
    }

    // Static Builder Class
    static class Builder {
        private BigDecimal Reward;
        private String Uid;
        private boolean IsParent;
        private String target;
        public Builder SetReward(BigDecimal Reward)
        {
            this.Reward = Reward;
            return this;
        }
        public Builder SetUid(String Uid)
        {
            SetUid(Uid);
            return this;
        }
        public Builder SetType(boolean IsParent)
        {
            this.IsParent = IsParent;
            return this;
        }
        public Builder Settarget(String target)
        {
            this.target = target;
            return this;
        }

        public Target Build()
        {
            Target temp = new Target(this);
            return temp;
        }
    }

}
