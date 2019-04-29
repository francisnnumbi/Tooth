/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smirl.tooth.listener;

/**
 *
 * @author CongoPublishers
 */
public interface OnConnectionListener {

    void onResponse(String response);

    void onError(int errorCode, String error);
}
