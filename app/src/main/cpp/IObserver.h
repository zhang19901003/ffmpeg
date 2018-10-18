//
// Created by zhangyapeng on 18-10-18.
//

#ifndef C_PROJECT_IOBSERVER_H
#define C_PROJECT_IOBSERVER_H
#include <vector>
#include <mutex>
#include "XData.h"
#include "XThread.h"

class IObserver : public XThread {
public:
    virtual void AddObs(IObserver *obs);

    virtual void Notify(XData data);
    void Update(XData data){

    }

protected:
    std::vector<IObserver *>obss;
    std::mutex mux;
};


#endif //C_PROJECT_IOBSERVER_H
