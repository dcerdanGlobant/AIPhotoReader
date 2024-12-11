//
//  ShareViewController.swift
//  iosApp
//
//  Created by Antonio Martinez Zarco on 3/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import SwiftUI

// MARK: - ShareViewController
final class ShareViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
}

// MARK: - Setup
private extension ShareViewController {
    func setupView() {
        guard let extensionContext, let itemProvider = (extensionContext.inputItems.first as? NSExtensionItem)?.attachments?.first else { return }
        let info = ShareViewInfo(itemProvider: itemProvider, extensionContext: extensionContext)
        let viewModel = ShareViewModel(with: info)
        let shareView = ShareView(with: viewModel)
        let hostingView = UIHostingController(rootView: shareView)
        hostingView.view.frame = view.frame
        view.addSubview(hostingView.view)
    }
}
