# -*- coding: utf-8 -*-
# @File  : LogHandle.py

import os
import logging
import time
from logging.handlers import RotatingFileHandler

def make_dir(make_dir_path):
    path = make_dir_path.strip()
    if not os.path.exists(path):
        os.makedirs(path)

def getLogHandler():
    log_dir_name = "gene-py-logs"
    log_file_name = 'logger-' + time.strftime('%Y-%m-%d', time.localtime(time.time())) + '.log'
    log_file_folder = "/opt/big-folder/logs" + os.sep + log_dir_name
    make_dir(log_file_folder)
    log_file_str = log_file_folder + os.sep + log_file_name

    logging.basicConfig(level=logging.INFO)
    file_log_handler = RotatingFileHandler(log_file_str, maxBytes=1024 * 1024 * 10, backupCount=10, encoding='UTF-8')
    formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(filename)s - %(funcName)s - %(lineno)s - %(message)s')
    file_log_handler.setFormatter(formatter)


    return file_log_handler
