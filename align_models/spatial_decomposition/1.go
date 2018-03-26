package example

import (
	"net/http"
	"strings"
	"regexp"
	"time"
)

func RenderPage(request *http.Request) map[string]interface{} {
	page := map[string]interface{}{}
	name := request.Form.Get("name")
	page["name"] = name
	urlPathName := strings.ToLower(name)
	urlPathName = regexp.MustCompile(`['\.]`).ReplaceAllString(
		urlPathName, "")
	urlPathName = regexp.MustCompile(`[^a-z0-9]+`).ReplaceAllString(
		urlPathName, "-")
	urlPathName = strings.Trim(urlPathName, "-")
	page["url"] = "/biz/" + urlPathName
	page["date_created"] = time.Now().In(time.UTC)
	return page
}

var page = map[string]pageItem{
	"name":         pageName,
	"url":          pageUrl,
	"date_created": pageDateCreated,
}

type pageItem func(*http.Request) interface{}

func pageName(request *http.Request) interface{} {
	return request.Form.Get("name")
}

func pageUrl(request *http.Request) interface{} {
	name := request.Form.Get("name")
	urlPathName := strings.ToLower(name)
	urlPathName = regexp.MustCompile(`['\.]`).ReplaceAllString(
		urlPathName, "")
	urlPathName = regexp.MustCompile(`[^a-z0-9]+`).ReplaceAllString(
		urlPathName, "-")
	urlPathName = strings.Trim(urlPathName, "-")
	return "/biz/" + urlPathName
}

func pageDateCreated(request *http.Request) interface{} {
	return time.Now().In(time.UTC)
}

