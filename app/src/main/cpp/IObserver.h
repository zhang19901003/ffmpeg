//
// Created by zhangyapeng on 18-11-2.
//

#ifndef C_PROJECT_IOBSERVER_H
#define C_PROJECT_IOBSERVER_H


class IObserver {
public:
    virtual void Update(float price) = 0;
};


#endif //C_PROJECT_IOBSERVER_H
