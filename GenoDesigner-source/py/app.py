# -*- coding: utf-8 -*-
# @File  : app.py

from flask import Flask, jsonify, request
from PostConvertor import test, allowed_file, ALLOWED_fa_EXTENSIONS, ALLOWED_gf_EXTENSIONS, con2gb
from PostBatchgene import ALLOWED_config_EXTENSIONS, ALLOWED_gb_EXTENSIONS, batchrun
from GenbankUtil import cutGbFile,cutFaFile
from LogHandler import getLogHandler
from Bio import SeqFeature
from DeleteFeature import deleteFeatureByType
from ReduceGenome import reduceGenome
from GlobalInsertion import globalInsertion
from ImportFeature import importFeature
from CodonReplace import codonReplace
from MergeFeature import parse_annotation_parent
app = Flask(__name__)
app.logger.addHandler(getLogHandler())
ctx = app.app_context()
ctx.push()


# @app.before_request
# def log_each_request():
#     print(1111)
#         request.method, request.path, request.remote_addr))

@app.errorhandler(500)
def server_error(error):
    print(dir(error))
    original = getattr(error, "original_exception", None)
    print(original)
    return jsonify({
        "code": 500,
        "msg": "Script errpr:"+str(original)
    })
@app.route("/hello")
def hello_world():
    return jsonify({
        "a": test(123),
        "b": 2
    })
@app.route("/mergeFeature", methods=['POST'])
def merge_feature():
    print(request.get_json()['path'])
    return parse_annotation_parent(request.get_json()['path'])
@app.route("/deleteFeature", methods=['POST'])
def delete_feature():
    print(request.get_json()['path'])
    print(request.get_json()['typeRemainMap'])
    return deleteFeatureByType(request.get_json()['path'],request.get_json()['path'],request.get_json()['typeList'],request.get_json()['typeRemainMap'],request.get_json()['operateId'])
@app.route("/reduceGenome", methods=['POST'])
def reduce_genome():
    print(request.get_json()['path'])
    print(request.get_json()['typeRemainMap'])
    return reduceGenome(request.get_json()['path'],request.get_json()['path'],request.get_json()['typeRemainMap'],request.get_json()['operateId'])
@app.route("/globalInsertion", methods=['POST'])
def global_insertion():
    print(request.get_json())
    return globalInsertion(request.get_json(),request.get_json()['path'],request.get_json()['path'],request.get_json()['operateId'])
@app.route("/codonReplace", methods=['POST'])
def codon_replace():
    print(request.get_json())
    return codonReplace(request.get_json()['path'],request.get_json()['path'],request.get_json()['codonDelList'],request.get_json()['codonReplace'],request.get_json()['operateId'])
@app.route("/importFeature", methods=['POST'])
def import_feature():
    print(request.get_json())
    return importFeature(request.get_json()['path'],request.get_json()['pathList'])
#fa转gb
@app.route('/faGffToGb', methods=['POST'])
def faGffToGb():
    fa_file_path = request.get_json()['fa']
    if not allowed_file(fa_file_path, ALLOWED_fa_EXTENSIONS):
        return jsonify({
        "code": 500,
        "msg": 'file format not valid'})
    gf_file_path = request.get_json()['gf']
    if not allowed_file(gf_file_path, ALLOWED_gf_EXTENSIONS):
        return jsonify({
        "code": 500,
        "msg": 'file format not valid'})
    out_file_path = request.get_json()['out']
    # call convertor
    con2gb(fa_file_path,
           gf_file_path,
           out_file_path)
    return jsonify({
        "code": 200,
        "msg": 'Success'})
@app.route('/convertHistory', methods=['POST'])
def convertHistory():
	configFile = request.get_json()['config']
	if not allowed_file(configFile, ALLOWED_config_EXTENSIONS):
		return jsonify({
        "code": 500,
        "msg": 'file format not valid'})
		
	gbFile = request.get_json()['gb']
	if not allowed_file(gbFile, ALLOWED_gb_EXTENSIONS):
		return jsonify({
        "code": 500,
        "msg": 'file format not valid'})

	outdir = request.get_json()['outdir']
	# call convertor
	batchrun(configFile, 
             gbFile, 
             outdir)
	return jsonify({
        "code": 200,
        "msg": 'Success'})
@app.route('/cutGb', methods=['POST'])
def cutGb():
	writeFileFolder = request.get_json()['writeFileFolder']
	readFilePath = request.get_json()['readFilePath']
	# call convertor
	return jsonify({
        "code": 200,
        "msg": 'Success',
        "data": cutGbFile(writeFileFolder,readFilePath)})
@app.route('/cutFa', methods=['POST'])
def cutFa():
	writeFileFolder = request.get_json()['writeFileFolder']
	readFilePath = request.get_json()['readFilePath']
	# call convertor
	return jsonify({
        "code": 200,
        "msg": 'success',
        "data": cutFaFile(writeFileFolder,readFilePath)})
if __name__ == "__main__":
    app.run(host='0.0.0.0',port=5003)
