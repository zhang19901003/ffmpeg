//
// Created by zhangyapeng on 18-11-12.
//

#ifndef C_PROJECT_SUNWUKONG_H
#define C_PROJECT_SUNWUKONG_H

#include <string>
#include "Monkey.h"
#include "XLog.h"

using namespace std;
class SunWuKong : public Monkey {
public:
    SunWuKong(string name){ m_strName = name; }
    ~SunWuKong(){
        LOGE("SunWuKong  ~SunWuKong");
    }


    SunWuKong(const SunWuKong &other) {
        LOGE("%s ,  调用拷贝构造函数",other.m_strName.c_str());
        m_strName = other.m_strName;
    }
    Monkey* Clone() {
        // 调用拷贝构造函数
        return new SunWuKong(*this);
    }
    void Play() {
        LOGE("%s , play gloden hoop stick",m_strName.c_str());
    }

private:
    string m_strName;

};


#endif //C_PROJECT_SUNWUKONG_H
