//
// Created by zhangyapeng on 18-11-2.
//

#include "Computer.h"

void Computer::SetmCpu(string cpu) { m_strCpu = cpu; }

void Computer::SetmMainboard(string mainboard) { m_strMainboard = mainboard; }

void Computer::SetmRam(string ram) { m_strRam = ram; }

void Computer::SetVideoCard(string videoCard) { m_strVideoCard = videoCard; }

string Computer::GetCPU() { return m_strCpu; }

string Computer::GetMainboard() { return m_strMainboard; }

string Computer::GetRam() { return m_strRam; }

string Computer::GetVideoCard() { return m_strVideoCard; }
