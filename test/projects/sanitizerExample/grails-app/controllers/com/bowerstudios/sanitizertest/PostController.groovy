package com.bowerstudios.sanitizertest

import org.springframework.dao.DataIntegrityViolationException

class PostController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [postInstanceList: Post.list(params), postInstanceTotal: Post.count()]
    }

    def create() {
        [postInstance: new Post(params)]
    }

    def save() {
        def postInstance = new Post(params)
        if (!postInstance.save(flush: true)) {
            render(view: "create", model: [postInstance: postInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'post.label', default: 'Post'), postInstance.id])
        redirect(action: "show", id: postInstance.id)
    }

    def show(Long id) {
        def postInstance = Post.get(id)
        if (!postInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), id])
            redirect(action: "list")
            return
        }

        [postInstance: postInstance]
    }

    def edit(Long id) {
        def postInstance = Post.get(id)
        if (!postInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), id])
            redirect(action: "list")
            return
        }

        [postInstance: postInstance]
    }

    def update(Long id, Long version) {
        def postInstance = Post.get(id)
        if (!postInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (postInstance.version > version) {
                postInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'post.label', default: 'Post')] as Object[],
                          "Another user has updated this Post while you were editing")
                render(view: "edit", model: [postInstance: postInstance])
                return
            }
        }

        postInstance.properties = params

        if (!postInstance.save(flush: true)) {
            render(view: "edit", model: [postInstance: postInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'post.label', default: 'Post'), postInstance.id])
        redirect(action: "show", id: postInstance.id)
    }

    def delete(Long id) {
        def postInstance = Post.get(id)
        if (!postInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), id])
            redirect(action: "list")
            return
        }

        try {
            postInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'post.label', default: 'Post'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'post.label', default: 'Post'), id])
            redirect(action: "show", id: id)
        }
    }
}
