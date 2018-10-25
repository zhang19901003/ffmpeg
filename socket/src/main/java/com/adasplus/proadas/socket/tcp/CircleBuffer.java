package com.adasplus.proadas.socket.tcp;


import android.util.Log;

public class CircleBuffer {
	private final Object mLock = new Object();
	private int mWritePointer;
	private int mReadPointer;
	private int mTotalSize;
	private byte [] mCircleBuffer;
	
	public CircleBuffer(int size) {
		mCircleBuffer = new byte[size];
		mTotalSize = size;
		mWritePointer = 0;
		mReadPointer = 0;
	}
	
	
	public void writeBuffer(byte[] data, int length){
		synchronized (mLock) {
			if(length  < 0 || length > data.length){
				return;
			}
			if(mWritePointer + length <= mTotalSize){
				System.arraycopy(data, 0, mCircleBuffer, mWritePointer, length);
				mWritePointer += length;
			} else {
				int left = mTotalSize - mWritePointer;
				System.arraycopy(data, 0, mCircleBuffer, mWritePointer, left);
				System.arraycopy(data, left, mCircleBuffer, 0, length - left);
				mWritePointer = length - left;
			}
		}
	}

	public int readBuffer(byte[] data, int length){
		synchronized (mLock){
			if(length > data.length|| length < 0 || mTotalSize == 0 || mReadPointer == mWritePointer){
				return -1;
			} else {
				if(mReadPointer + length <= mWritePointer){
					System.arraycopy(mCircleBuffer, mReadPointer, data, 0, length);
					mReadPointer += length;
					return length;
				} else if(mReadPointer + length > mWritePointer){
					if(mReadPointer <= mWritePointer){
						int copyLen = mWritePointer - mReadPointer;
						System.arraycopy(mCircleBuffer, mReadPointer, data, 0, copyLen);
						mReadPointer += copyLen;
						return copyLen;
					} else if(mReadPointer > mWritePointer){
						if(mReadPointer + length <= mTotalSize){
							System.arraycopy(mCircleBuffer, mReadPointer, data, 0, length);
							mReadPointer += length;
							return length;
						} else if(mReadPointer + length > mTotalSize){
							int left = mTotalSize - mReadPointer;
							System.arraycopy(mCircleBuffer, mReadPointer, data, 0, left);
							int begin = length - (mTotalSize - mReadPointer);
							if(begin <= mWritePointer){
								System.arraycopy(mCircleBuffer, 0, data, left, begin);
								mReadPointer = begin;
								return length;
							} else {
								System.arraycopy(mCircleBuffer, 0, data, left, mWritePointer);
								mReadPointer = mWritePointer;
								return left + mWritePointer;
							}
						}
					}
				}
			}
		}
		return -1;
	}
	
	
	public int readBuffer(byte[] data){
		synchronized (mLock) {
			if(mTotalSize == 0 || mReadPointer == mWritePointer){
				return -1;
			} else {
				if(mReadPointer + data.length <= mWritePointer){
					System.arraycopy(mCircleBuffer, mReadPointer, data, 0, data.length);
					mReadPointer += data.length;
					return data.length;
				} else if(mReadPointer + data.length > mWritePointer){
					if(mReadPointer <= mWritePointer){
						int copyLen = mWritePointer - mReadPointer;
						System.arraycopy(mCircleBuffer, mReadPointer, data, 0, copyLen);
						mReadPointer += copyLen;
						return copyLen;
					} else if(mReadPointer > mWritePointer){
						if(mReadPointer + data.length <= mTotalSize){
							System.arraycopy(mCircleBuffer, mReadPointer, data, 0, data.length);
							mReadPointer += data.length;
							return data.length;
						} else if(mReadPointer + data.length > mTotalSize){
							int left = mTotalSize - mReadPointer;
							System.arraycopy(mCircleBuffer, mReadPointer, data, 0, left);
							int begin = data.length - (mTotalSize - mReadPointer);
							if(begin <= mWritePointer){
								System.arraycopy(mCircleBuffer, 0, data, left, begin);
								mReadPointer = begin;
								return data.length;
							} else {
								System.arraycopy(mCircleBuffer, 0, data, left, mWritePointer);
								mReadPointer = mWritePointer;
								return left + mWritePointer;
							}
						}
					}
				}
			}
		}
		return -1;
	}

	//Invoke this method.
	public int readBuffer2(byte[] data, int  length){
		synchronized (mLock){
			if(length > data.length|| length < 0 || mTotalSize == 0 || mReadPointer == mWritePointer){
				return -1;
			} else {
				if(mReadPointer + length <= mWritePointer){
					System.arraycopy(mCircleBuffer, mReadPointer, data, 0, length);
					mReadPointer += length;
					return length;
				} else if(mReadPointer + length > mWritePointer){
					if(mReadPointer <= mWritePointer){
						return -1;
					} else if(mReadPointer > mWritePointer){
						if(mReadPointer + length <= mTotalSize){
							System.arraycopy(mCircleBuffer, mReadPointer, data, 0, length);
							mReadPointer += length;
							return length;
						} else if(mReadPointer + length > mTotalSize){
							int left = mTotalSize - mReadPointer;
							System.arraycopy(mCircleBuffer, mReadPointer, data, 0, left);
							int begin = length - (mTotalSize - mReadPointer);
							if(begin <= mWritePointer){
								System.arraycopy(mCircleBuffer, 0, data, left, begin);
								mReadPointer = begin;
								return length;
							} else {
								return -1;
							}
						}
					}
				}
			}
		}
		return -1;
	}

	public int getReadPointer() {
		return mReadPointer;
	}

	public int getWritePointer() {
		return mWritePointer;
	}
}
