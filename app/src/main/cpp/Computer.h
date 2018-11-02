//
// Created by zhangyapeng on 18-11-2.
//

#ifndef C_PROJECT_COMPUTER_H
#define C_PROJECT_COMPUTER_H

#include <iostream>

using namespace std;
class Computer {
private:
    string m_strCpu;  // CPU
    string m_strMainboard;  // 主板
    string m_strRam;  // 内存
    string m_strVideoCard;  // 显卡

public:
    void SetmCpu(string cpu);
    void SetmMainboard(string mainboard) ;
    void SetmRam(string ram);
    void SetVideoCard(string videoCard) ;

    string GetCPU();
    string GetMainboard() ;
    string GetRam();
    string GetVideoCard() ;

};


#endif //C_PROJECT_COMPUTER_H
